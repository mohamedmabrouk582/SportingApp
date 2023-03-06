package com.mabrouk.sportingapp.persentaion.states

import com.mabrouk.sportingapp.domain.models.Event


/**
 * @name Mohamed Mabrouk
 * Copyright (c) 2/20/23
 */
sealed class EventStates {
    object IDLE : EventStates()
    data class LoadEvents(val events: ArrayList<Event>) : EventStates()
    data class Error(val error: String) : EventStates()
}
