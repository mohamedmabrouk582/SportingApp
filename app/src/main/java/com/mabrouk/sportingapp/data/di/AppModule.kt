package com.mabrouk.sportingapp.data.di

import com.mabrouk.sportingapp.data.api.EventApi
import com.mabrouk.sportingapp.data.repository.EventRepository
import com.mabrouk.sportingapp.data.repository.LiveEventRepository
import com.mabrouk.sportingapp.domain.usecases.EventUseCases
import com.mabrouk.sportingapp.domain.usecases.LiveEventUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

/**
 * @name Mohamed Mabrouk
 * Copyright (c) 2/20/23
 */
@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun getEventApi(retrofit: Retrofit): EventApi =
        retrofit.create(EventApi::class.java)

    @Provides
    @Singleton
    fun getEventUseCase(repository: EventRepository) =
        EventUseCases(repository)

    @Provides
    @Singleton
    fun getLiveEventsUseCase(repository: LiveEventRepository) =
        LiveEventUseCase(repository)
}