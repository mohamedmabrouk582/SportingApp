package com.mabrouk.sportingapp.domain.repository

import com.mabrouk.core.network.Result
import com.mabrouk.sportingapp.domain.models.EventResponse
import kotlinx.coroutines.flow.Flow

/**
 * @name Mohamed Mabrouk
 * Copyright (c) 3/1/23
 */
interface LiveEventsDefaultRepository {
    fun requestLiveEvents() : Flow<Result<EventResponse>>
}