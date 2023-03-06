package com.mabrouk.sportingapp.data.api

import com.mabrouk.core.network.getApiKey
import com.mabrouk.sportingapp.domain.models.EventResponse
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * @name Mohamed Mabrouk
 * Copyright (c) 2/20/23
 */
interface EventApi {

    @GET("football/?met=Fixtures")
    suspend fun requestEventsAsync(
        @Query("from") from: String,
        @Query("to") to: String,
        @Query("APIkey") APIkey: String = "0d4c62d88469205dc1d674b39137a1db779a2153cbfc1f0d0d3d2a773946522e"
    ): Response<EventResponse>

    @GET("football/?met=Livescore")
    suspend fun requestLiveEventsAsync(
        @Query("APIkey") APIkey: String = "0d4c62d88469205dc1d674b39137a1db779a2153cbfc1f0d0d3d2a773946522e"
    ): Response<EventResponse>

}