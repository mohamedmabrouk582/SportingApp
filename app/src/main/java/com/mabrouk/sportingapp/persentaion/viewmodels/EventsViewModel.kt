package com.mabrouk.sportingapp.persentaion.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mabrouk.core.network.Result
import com.mabrouk.sportingapp.domain.models.Event
import com.mabrouk.sportingapp.domain.usecases.EventUseCases
import com.mabrouk.sportingapp.domain.usecases.LiveEventUseCase
import com.mabrouk.sportingapp.persentaion.states.EventStates
import com.mabrouk.sportingapp.persentaion.toNewObject
import com.mabrouk.sportingapp.persentaion.view.allEvents
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * @name Mohamed Mabrouk
 * Copyright (c) 2/20/23
 */
@HiltViewModel
class EventsViewModel @Inject constructor(
    val useCases: EventUseCases
) : ViewModel() {
    private val _eventsStates = MutableStateFlow<EventStates>(EventStates.IDLE)
    val eventsStates: StateFlow<EventStates> = _eventsStates
    var isRefreshing = MutableStateFlow(true)
    var fromDate: String = ""
    var toDate: String = ""


    fun refresh(from: String?=null, to: String?=null) {
        to?.let { toDate=it }
        from?.let { fromDate=it }
        isRefreshing.value = true
        getEvents(fromDate, toDate)
    }

    fun getEvents(from: String, to: String) {
        this.fromDate = from
        this.toDate = to
        viewModelScope.launch {
            useCases.requestEvents(from, to).collect {
                when (it) {
                    is Result.NoInternetConnect -> _eventsStates.value = EventStates.Error(it.error)
                    is Result.OnFailure -> _eventsStates.value =
                        EventStates.Error(it.throwable.message.toString())
                    is Result.OnSuccess -> {
                        isRefreshing.value = false
                        val eventsWithHeader = arrayListOf<Event>()
                        it.data.result.groupBy {
                            it.league_name + it.country_name
                        }.values.forEachIndexed { index, events ->
                            eventsWithHeader.add(
                                events.first().toNewObject().apply {
                                    event_key = index
                                    isHeader = true
                                }
                            )
                            eventsWithHeader.addAll(events)

                        }
                        _eventsStates.value =
                            EventStates.LoadEvents(eventsWithHeader)
                    }
                    else -> {}
                }
            }
        }
    }
}