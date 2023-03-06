package com.mabrouk.sportingapp.domain.usecases

import com.mabrouk.core.network.Result
import com.mabrouk.sportingapp.domain.models.EventResponse
import com.mabrouk.sportingapp.domain.repository.EventDefaultRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * @name Mohamed Mabrouk
 * Copyright (c) 2/20/23
 */
class EventUseCases @Inject constructor(val repository: EventDefaultRepository) {
     fun requestEvents(from: String, to: String): Flow<Result<EventResponse>> {
        return repository.requestEvents(from, to)
    }
}