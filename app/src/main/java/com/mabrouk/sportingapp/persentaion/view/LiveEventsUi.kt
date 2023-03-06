package com.mabrouk.sportingapp.persentaion.view

import android.annotation.SuppressLint
import android.net.Uri
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.google.android.gms.maps.model.LatLng
import com.google.gson.Gson
import com.mabrouk.core.utils.getCurrentDate
import com.mabrouk.sportingapp.domain.models.Event
import com.mabrouk.sportingapp.persentaion.states.EventStates
import com.mabrouk.sportingapp.persentaion.viewmodels.LiveEventsViewModel
import kotlinx.coroutines.flow.MutableStateFlow

/**
 * @name Mohamed Mabrouk
 * Copyright (c) 3/1/23
 */

val allLiveEvents = MutableStateFlow<ArrayList<Event>?>(null)

lateinit var liveViewModel: LiveEventsViewModel


fun displayLiveEventsScreen(
    mLiveViewModel: LiveEventsViewModel
) {
    liveViewModel = mLiveViewModel
    liveViewModel.getLiveEvents()
}


@Composable
fun liveEventScreen(navHostController: NavHostController) {
    loadLiveViews {
        val data = Uri.encode(Gson().toJson(it))
        navHostController.navigate("${Route.LIVE_DETAILS.type}/$data") {
            launchSingleTop = true
        }
    }
    handleLiveEventsStates()
}

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun handleLiveEventsStates() {
    when (val values = liveViewModel.eventsStates.collectAsState().value) {
        is EventStates.Error -> {
        }
        is EventStates.LoadEvents -> {
            allLiveEvents.value = values.events
            liveViewModel.isRefreshing.value = false
        }
        else -> {}
    }
}


@OptIn(ExperimentalMaterialApi::class)
@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun loadLiveViews(onClick: (event: Event) -> Unit) {
    val data = allLiveEvents.collectAsState()
    val isRefreshing = liveViewModel.isRefreshing.collectAsState()
    val pullRefresh = rememberPullRefreshState(refreshing = isRefreshing.value, onRefresh = {
        liveViewModel.refresh()
    })
    Box(
        modifier = Modifier
            .fillMaxSize()
            .pullRefresh(pullRefresh)
    ) {
        if (isRefreshing.value) {
            eventsShimmer()
        } else {
            listEvents(data.value ?: arrayListOf(), onClick)
        }
        PullRefreshIndicator(
            refreshing = isRefreshing.value,
            state = pullRefresh,
            Modifier.align(Alignment.TopCenter)
        )
    }
}