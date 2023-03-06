package com.mabrouk.sportingapp.data.repository

import android.content.Context
import com.mabrouk.core.network.Result
import com.mabrouk.core.network.executeCall
import com.mabrouk.core.network.executeCall2
import com.mabrouk.sportingapp.data.api.EventApi
import com.mabrouk.sportingapp.domain.models.EventResponse
import com.mabrouk.sportingapp.domain.repository.EventDefaultRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * @name Mohamed Mabrouk
 * Copyright (c) 2/20/23
 */
class EventRepository @Inject constructor(
    @ApplicationContext val context: Context,
    private val api: EventApi
) : EventDefaultRepository {

    override fun requestEvents(from: String, to: String): Flow<Result<EventResponse>> {
        return executeCall(context) {
            api.requestEventsAsync(from, to)
        }
    }


}