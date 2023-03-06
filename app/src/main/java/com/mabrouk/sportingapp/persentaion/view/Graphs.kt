package com.mabrouk.sportingapp.persentaion.view

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.mabrouk.sportingapp.domain.models.Event
import com.mabrouk.sportingapp.persentaion.viewmodels.EventsViewModel
import com.mabrouk.sportingapp.persentaion.viewmodels.LiveEventsViewModel

/**
 * @name Mohamed Mabrouk
 * Copyright (c) 3/1/23
 */

fun NavGraphBuilder.eventsGraph(navHostController: NavHostController, viewModel: EventsViewModel) {
    navigation(startDestination = Route.EVENTS.type, route = ItemBottomBar.Events.route) {
        displayEventsScreen(viewModel)
        composable(Route.EVENTS.type) {
            eventScreen(navHostController = navHostController)
        }

        composable("${Route.EVENT_DETAILS.type}/{event}", arguments =
        listOf(
            navArgument("event") {
                type = EventArgs()
            }
        )
        ) {
            val event = it.arguments?.getParcelable<Event>("event")
            event?.let {
                eventDetailScreen(event)
            }
        }
    }
}


fun NavGraphBuilder.liveEventsGraph(navHostController: NavHostController, viewModel: LiveEventsViewModel) {
    navigation(startDestination = Route.LIVES.type, route = ItemBottomBar.Lives.route) {
        displayLiveEventsScreen(viewModel)
        composable(Route.LIVES.type) {
            liveEventScreen(navHostController = navHostController)
        }

        composable("${Route.LIVE_DETAILS.type}/{event}", arguments =
        listOf(
            navArgument("event") {
                type = EventArgs()
            }
        )
        ) {
            val event = it.arguments?.getParcelable<Event>("event")
            event?.let {
                eventDetailScreen(event)
            }
        }
    }
}

