@file:OptIn(ExperimentalMaterialApi::class, ExperimentalAnimationApi::class)

package com.mabrouk.sportingapp.persentaion.view

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.DatePickerDialog.*
import android.content.Context
import android.content.res.Configuration
import android.net.Uri
import android.os.Bundle
import android.widget.DatePicker
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.*
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.google.android.gms.maps.model.LatLng
import com.google.gson.Gson
import com.mabrouk.core.network.toArrayList
import com.mabrouk.core.utils.convertDate
import com.mabrouk.core.utils.getDate
import com.mabrouk.sportingapp.R
import com.mabrouk.sportingapp.persentaion.states.EventStates
import com.mabrouk.sportingapp.persentaion.viewmodels.EventsViewModel
import com.mabrouk.sportingapp.domain.models.Event
import com.mabrouk.sportingapp.domain.models.StartingLineup
import com.mabrouk.sportingapp.persentaion.JsonNavType
import com.mabrouk.sportingapp.persentaion.getEvent
import com.mabrouk.sportingapp.persentaion.parseToObject
import com.mabrouk.sportingapp.persentaion.theme.greenColor
import com.valentinilk.shimmer.shimmer
import kotlinx.coroutines.flow.MutableStateFlow
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.Month
import java.time.Year
import java.util.*
import kotlin.collections.ArrayList

/**
 * @name Mohamed Mabrouk
 * Copyright (c) 2/20/23
 */

val allEvents = MutableStateFlow<ArrayList<Event>?>(null)
lateinit var viewModel: EventsViewModel


fun displayEventsScreen(
    mViewModel: EventsViewModel
) {
    viewModel = mViewModel
    viewModel.getEvents("2023-03-3", "2023-03-3")
}


@Composable
fun eventScreen(navHostController: NavHostController) {
    loadViews {
        val data = Uri.encode(Gson().toJson(it))
        navHostController.navigate("${Route.EVENT_DETAILS.type}/$data") {
            launchSingleTop = true
        }
    }
    handleEventsStates(viewModel = viewModel)
}

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun handleEventsStates(viewModel: EventsViewModel) {
    when (val values = viewModel.eventsStates.collectAsState().value) {
        is EventStates.Error -> {
        }
        is EventStates.LoadEvents -> {
            allEvents.value = values.events
            viewModel.isRefreshing.value = false
        }
        else -> {}
    }
}

@OptIn(ExperimentalMaterialApi::class)
@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun loadViews(onClick: (event: Event) -> Unit) {
    val data = allEvents.collectAsState()
    val isRefreshing = viewModel.isRefreshing.collectAsState()
    val pullRefresh = rememberPullRefreshState(refreshing = isRefreshing.value, onRefresh = {
        viewModel.refresh()
    })
    Box(
        modifier = Modifier
            .fillMaxSize()
            .pullRefresh(pullRefresh)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            showDate()
            if (isRefreshing.value) {
                eventsShimmer()
            } else {
                listEvents(data.value ?: arrayListOf(), onClick)
            }
        }

        PullRefreshIndicator(
            refreshing = isRefreshing.value,
            state = pullRefresh,
            Modifier.align(Alignment.TopCenter)
        )
    }

}

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun listEvents(events: ArrayList<Event>, onClick: (event: Event) -> Unit) {
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        items(items = events, key = { event ->
            event.event_key
        }) {
            if (it.isHeader) header(event = it)
            else eventItemView(item = it, onClick)
        }
    }

}

@Composable
fun eventsShimmer() {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(10) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp)
                    .padding(10.dp)
                    .background(Color.DarkGray, shape = RoundedCornerShape(10.dp))
                    .shimmer(),
                elevation = 5.dp
            ) {
                Text(text = "")
            }
        }
    }
}


@Composable
fun eventItemView(item: Event, onClick: (event: Event) -> Unit) {
    var rotate by remember {
        mutableStateOf(item.isExpand)
    }
    val isStart = (item.event_final_result?.trim()?.length ?: 0) > 1

    if (item.homeFormationLineups.isNullOrEmpty()) {
        item.lineups?.home_team?.starting_lineups?.let {
            it.sortBy { it.player_position }
            if (it.isNotEmpty()) {
                val formation =
                    if (item.event_home_formation.isNullOrEmpty()) "4-4-2" else item.event_home_formation
                item.homeFormationLineups = formatFormation(formation, it)
            }
        }
    }

    if (item.awayFormationLineups.isNullOrEmpty()) {
        item.lineups?.away_team?.starting_lineups?.let {
            it.sortBy { it.player_position }
            if (it.isNotEmpty()) {
                val formation =
                    if (item.event_away_formation.isNullOrEmpty()) "4-4-2" else item.event_away_formation
                item.awayFormationLineups = formatFormation(formation, it)
            }
        }
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .background(Color.DarkGray, shape = RoundedCornerShape(10.dp))
            .clickable { onClick(item) }, elevation = 5.dp

    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp)
        ) {
            showLiveCircle(
                isLive = item.event_live == "1" && item.event_status != "Finished",
                Modifier.align(Alignment.Start)
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        top = 5.dp, bottom = 5.dp
                    ), horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    CircleImage(url = item.home_team_logo ?: "", item.event_home_team ?: "")

                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = if (isStart) item.event_final_result ?: "" else item.event_time
                                ?: "",
                        )
                        notStarting(item.event_status)

                    }
                    CircleImage(url = item.away_team_logo ?: "", item.event_away_team ?: "")
                }

            }
            showMore(event = item, rotate) {
                rotate = !rotate
                item.isExpand = rotate
            }

        }
    }
}

@Composable
fun showMore(event: Event, rotate: Boolean, onClick: () -> Unit) {
    val degree by animateFloatAsState(if (rotate) 180f else 0f)
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp)
    ) {
        Icon(Icons.Default.KeyboardArrowDown,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .clickable {
                    onClick()
                }
                .rotate(degree),
            contentDescription = "")
        AnimatedVisibility(visible = rotate) {
            teamFormation(event)
        }
    }
}

fun formatFormation(
    formation: String, data: ArrayList<StartingLineup>
): ArrayList<ArrayList<StartingLineup>> {
    data.sortBy {
        it.player_position
    }
    val split = formation.split("-") // "4-4-2"
    val list: ArrayList<ArrayList<StartingLineup>> = arrayListOf()
    var strat = 1
    for (i in split) {
        try {
            val to = strat + (i.toInt())
            val l = data.subList(strat, to).toArrayList()
            list.add(l)
            strat += i.toInt()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    return list
}

@Composable
fun notStarting(status: String?) {
    Box(
        modifier = Modifier.border(
            width = 1.dp, color = Color.Red, shape = RoundedCornerShape(
                5.dp
            )
        )
    ) {
        Text(
            text = if (status.isNullOrEmpty()) "No Start Yet" else status,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(5.dp)
        )
    }
}

@Composable
fun showLiveCircle(isLive: Boolean, modifier: Modifier) {
    if (isLive) {
        val infinityAnimation = rememberInfiniteTransition()
        val color by infinityAnimation.animateColor(
            initialValue = Color.Gray, targetValue = Color.Red, animationSpec = infiniteRepeatable(
                animation = tween(durationMillis = 600, easing = LinearEasing),
                repeatMode = RepeatMode.Reverse
            )
        )
        Row(
            modifier = modifier.padding(top = 5.dp, start = 5.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(10.dp)
                    .background(color, shape = CircleShape)
            )
            Text(
                modifier = Modifier.padding(start = 5.dp),
                text = "Live",
                color = Color.Red,
                fontSize = 10.sp
            )
        }

    }
}

@Composable
fun header(event: Event) {
    Row(
        modifier = Modifier
            .padding(15.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current).data(event.league_logo ?: "")
                .placeholder(R.drawable.place_holder).error(R.drawable.place_holder).build(),
            modifier = Modifier
                .size(50.dp)
                .clip(RoundedCornerShape(10.dp)),
            contentDescription = event.league_logo ?: "",
            contentScale = ContentScale.Fit
        )
        Spacer(modifier = Modifier.size(10.dp))
        Text(text = event.league_name ?: "")
    }
}


@Composable
fun CircleImage(url: String, teamName: String = "") {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .width(80.dp)
            .clickable {
            }
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current).data(url)
                .placeholder(R.drawable.place_holder).error(R.drawable.place_holder).build(),
            modifier = Modifier
                .size(70.dp)
                .clip(RoundedCornerShape(10.dp)),
            contentDescription = url,
            contentScale = ContentScale.Fit
        )
        Spacer(modifier = Modifier.size(5.dp))
        Text(text = teamName, modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center)
    }
}

@Composable
fun teamFormation(event: Event) {
    ConstraintLayout(modifier = Modifier.fillMaxWidth()) {
        val (homeGoalK, home, awayGoalK, away, imgBg, homeHeader, awayHeader) = createRefs()
        val guideLine = createGuidelineFromTop(.5f)
        teamHeader(url = event.home_team_logo ?: "",
            name = event.event_home_team ?: "",
            formation = event.event_home_formation ?: "4-4-2",
            modifier = Modifier.constrainAs(homeHeader) {
                top.linkTo(parent.top, margin = 0.dp)
                start.linkTo(parent.start, margin = 0.dp)
                end.linkTo(parent.end, margin = 0.dp)
            })

        Image(painter = painterResource(id = R.drawable.information),
            contentDescription = "",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .fillMaxSize()
                .aspectRatio(9f / 16f)
                .constrainAs(imgBg) {
                    top.linkTo(homeHeader.bottom, margin = 0.dp)
                    bottom.linkTo(awayHeader.top, margin = 0.dp)
                    start.linkTo(parent.start, margin = 0.dp)
                    end.linkTo(parent.end, margin = 0.dp)
                })

        event.lineups?.home_team?.starting_lineups?.firstOrNull()?.let {
            playerPosition(name = it.player,
                number = it.player_number.toString(),
                Modifier.constrainAs(
                    homeGoalK
                ) {
                    top.linkTo(imgBg.top, margin = 5.dp)
                    start.linkTo(parent.start, margin = 0.dp)
                    end.linkTo(parent.end, margin = 0.dp)
                })
        }



        event.homeFormationLineups?.let { list ->
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .constrainAs(home) {
                        top.linkTo(homeGoalK.bottom, margin = 5.dp)
                        start.linkTo(parent.start, margin = 0.dp)
                        end.linkTo(parent.end, margin = 0.dp)
                        bottom.linkTo(guideLine)
                    }, horizontalAlignment = Alignment.CenterHorizontally
            ) {
                list.forEach {
                    formationLine(players = it, modifier = Modifier.padding(top = 15.dp))
                }
            }
        }



        event.lineups?.away_team?.starting_lineups?.firstOrNull()?.let {
            playerPosition(name = it.player,
                number = it.player_number.toString(),
                Modifier.constrainAs(
                    awayGoalK
                ) {
                    bottom.linkTo(imgBg.bottom, margin = 5.dp)
                    start.linkTo(parent.start, margin = 0.dp)
                    end.linkTo(parent.end, margin = 0.dp)
                })
        }

        event.awayFormationLineups?.let { list ->
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .constrainAs(away) {
                        bottom.linkTo(awayGoalK.top, margin = 5.dp)
                        start.linkTo(parent.start, margin = 0.dp)
                        end.linkTo(parent.end, margin = 0.dp)
                        top.linkTo(guideLine)

                    }, horizontalAlignment = Alignment.CenterHorizontally
            ) {
                for (i in list.size - 1 downTo 0) {
                    formationLine(players = list[i], modifier = Modifier.padding(top = 15.dp))
                }
            }

        }

        teamHeader(url = event.away_team_logo ?: "",
            name = event.event_away_team ?: "",
            formation = event.event_away_formation ?: "4-4-2",
            modifier = Modifier.constrainAs(awayHeader) {
                top.linkTo(imgBg.bottom, margin = 0.dp)
                start.linkTo(parent.start, margin = 0.dp)
                end.linkTo(parent.end, margin = 0.dp)
                bottom.linkTo(parent.bottom, margin = 0.dp)
            })


    }
}

@Composable
fun formationLine(players: ArrayList<StartingLineup>, modifier: Modifier) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        for (player in players) {
            playerPosition(
                name = player.player,
                number = player.player_number.toString(),
                modifier = Modifier.padding(2.dp)
            )
        }
    }
}

@Composable
fun teamHeader(url: String, name: String, formation: String, modifier: Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(greenColor),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current).data(url)
                .placeholder(R.drawable.place_holder).error(R.drawable.place_holder).build(),
            modifier = Modifier
                .size(50.dp)
                .padding(10.dp),
            contentDescription = url,
            contentScale = ContentScale.Fit
        )
        Text(text = name, color = Color.White)
        Text(
            text = formation,
            color = Color.White,
            textAlign = TextAlign.End,
            modifier = Modifier
                .weight(1f)
                .padding(end = 10.dp)
        )
    }
}


@Composable
fun playerPosition(name: String, number: String, modifier: Modifier) {
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                .size(20.dp)
                .background(Color.White, CircleShape)
        ) {
            Text(text = number, fontSize = 10.sp, modifier = Modifier.align(Alignment.Center))
        }
        Text(text = name, fontSize = 10.sp, color = Color.White, textAlign = TextAlign.Center)
    }
}


class EventPreviewParams : PreviewParameterProvider<Event> {
    override val values: Sequence<Event>
        get() = sequenceOf(
            getEvent
        )
}

@Composable
fun showDate() {
    var date by remember {
        mutableStateOf("Date Picker")
    }
    val context = LocalContext.current
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .wrapContentSize(Alignment.TopStart)
            .border(width = .5.dp, MaterialTheme.colors.onSurface.copy(alpha = .5f))
            .clickable {
                showDatePicker(context = context) {
                    date = it
                    viewModel.refresh(date, date)
                }
            }
    ) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            val (label, iconView) = createRefs()
            Text(
                text = date,
                color = MaterialTheme.colors.onSurface,
                modifier = Modifier
                    .fillMaxWidth()
                    .constrainAs(label) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(iconView.start)
                        width = Dimension.fillToConstraints
                    }
            )

            Icon(
                imageVector = Icons.Default.DateRange,
                contentDescription = null,
                modifier = Modifier
                    .size(20.dp, 20.dp)
                    .constrainAs(iconView) {
                        end.linkTo(parent.end)
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                    },
                tint = MaterialTheme.colors.onSurface
            )

        }
    }
}

fun showDatePicker(context: Context, onDate: (date: String) -> Unit) {

    val year: Int
    val month: Int
    val day: Int

    val calendar = Calendar.getInstance()
    year = calendar.get(Calendar.YEAR)
    month = calendar.get(Calendar.MONTH)
    day = calendar.get(Calendar.DAY_OF_MONTH)

    DatePickerDialog(
        context,
        { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
            onDate("$year-${month+1}-$dayOfMonth")
        }, year, month, day
    ).show()
}


@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO or Configuration.UI_MODE_TYPE_NORMAL
)
@Composable
fun preview(
    @PreviewParameter(EventPreviewParams::class) event: Event
) {
    showDate()

    // teamFormation(event)
}


enum class Route(val type: String) {
    EVENTS("Events"), EVENT_DETAILS("EventDetails"), LIVES("Live"), LIVE_DETAILS("LiveDetails")
}

class EventArg : JsonNavType<Event>() {
    override fun fromJsonParse(value: String): Event {
        return parseToObject<Event>(value)!!
    }

    override fun Event.getJsonParse(): String {
        return Gson().toJson(this)
    }
}

class EventArgs : NavType<Event>(isNullableAllowed = false) {

    override fun get(bundle: Bundle, key: String): Event? {
        return bundle.getParcelable(key)
    }

    override fun parseValue(value: String): Event {
        return Gson().fromJson(value, Event::class.java)
    }

    override fun put(bundle: Bundle, key: String, value: Event) {
        return bundle.putParcelable(key, value)
    }

}





