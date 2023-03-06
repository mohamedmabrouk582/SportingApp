package com.mabrouk.sportingapp.data.repository

import android.content.Context
import com.mabrouk.core.network.Result
import com.mabrouk.core.network.executeCall
import com.mabrouk.sportingapp.data.api.EventApi
import com.mabrouk.sportingapp.domain.models.EventResponse
import com.mabrouk.sportingapp.domain.repository.LiveEventsDefaultRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * @name Mohamed Mabrouk
 * Copyright (c) 3/1/23
 */
class LiveEventRepository @Inject constructor(
    @ApplicationContext val context: Context,
    val api: EventApi
) : LiveEventsDefaultRepository {
    override fun requestLiveEvents(): Flow<Result<EventResponse>> {
        return executeCall(context) {
            api.requestLiveEventsAsync()
        }
    }
}