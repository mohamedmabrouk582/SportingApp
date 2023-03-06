package com.mabrouk.sportingapp.domain.repository

import com.mabrouk.core.network.Result
import com.mabrouk.sportingapp.domain.models.EventResponse
import kotlinx.coroutines.flow.Flow

/**
 * @name Mohamed Mabrouk
 * Copyright (c) 2/20/23
 */
interface EventDefaultRepository {
    fun requestEvents(from: String, to: String): Flow<Result<EventResponse>>
}