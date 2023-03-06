package com.mabrouk.sportingapp.persentaion.view

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.DrawableRes
import androidx.annotation.RequiresApi
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.*
import com.mabrouk.core.utils.getCurrentDate
import com.mabrouk.sportingapp.R
import com.mabrouk.sportingapp.persentaion.viewmodels.EventsViewModel
import com.mabrouk.sportingapp.persentaion.viewmodels.LiveEventsViewModel

/**
 * @name Mohamed Mabrouk
 * Copyright (c) 3/1/23
 */

val items = listOf(
    ItemBottomBar.Events,
    ItemBottomBar.Lives
)

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "ComposableNavGraphInComposeScope")
@Composable
fun home() {
    val viewModel: EventsViewModel = hiltViewModel()
    val liveViewModel: LiveEventsViewModel = hiltViewModel()
    val navHostController = rememberNavController()
    Scaffold(bottomBar = {
        BottomNavigation {
            val navBackStackEntry by navHostController.currentBackStackEntryAsState()
            val currentDestination = navBackStackEntry?.destination
            items.forEach { screen ->
                BottomNavigationItem(
                    icon = {
                        Icon(
                            painter = painterResource(screen.iconResource),
                            contentDescription = ""
                        )
                    },
                    label = { Text(text = stringResource(id = screen.resourceId)) },
                    selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                    onClick = {
                        navHostController.navigate(screen.route) {
                            popUpTo(navHostController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }

        }
    }) {
        Box(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        ) {
            NavHost(
                navController = navHostController,
                startDestination = ItemBottomBar.Events.route
            ) {
                eventsGraph(navHostController, viewModel)
                liveEventsGraph(navHostController, liveViewModel)
            }
        }
    }
}

sealed class ItemBottomBar(
    val route: String,
    @StringRes val resourceId: Int,
    @DrawableRes val iconResource: Int
) {
    object Events :
        ItemBottomBar("Event", resourceId = R.string.txt_events, R.drawable.baseline_event_24)

    object Lives : ItemBottomBar("Lives", R.string.txt_lives, R.drawable.baseline_live_tv_24)
}