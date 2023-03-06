package com.mabrouk.sportingapp.domain.usecases

import com.mabrouk.core.network.Result
import com.mabrouk.sportingapp.domain.models.EventResponse
import com.mabrouk.sportingapp.domain.repository.LiveEventsDefaultRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * @name Mohamed Mabrouk
 * Copyright (c) 3/1/23
 */
class LiveEventUseCase @Inject constructor(val repository: LiveEventsDefaultRepository) {
    fun requestLiveEvents() : Flow<Result<EventResponse>>{
        return repository.requestLiveEvents()
    }
}