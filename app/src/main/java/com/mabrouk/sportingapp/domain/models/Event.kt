package com.mabrouk.sportingapp.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * @name Mohamed Mabrouk
 * Copyright (c) 2/20/23
 */
@Parcelize
data class Event(
    var event_key: Int,
    val event_date: String?,
    val event_time: String?,
    val event_home_team: String?,
    val home_team_key: Int?,
    val event_away_team: String?,
    val away_team_key: Int?,
    val event_halftime_result: String?,
    val event_final_result: String?,
    val event_ft_result: String?,
    val event_penalty_result: String?,
    val event_status: String?,
    val country_name: String?,
    val league_name: String?,
    val league_key: Int?,
    val league_round: String?,
    val league_season: String?,
    val event_live: String?,
    val event_stadium: String?,
    val event_referee: String?,
    val home_team_logo: String?,
    val away_team_logo: String?,
    val event_country_key: Int?,
    val league_logo: String?,
    val country_logo: String?,
    val event_home_formation: String?,
    val event_away_formation: String?,
    val fk_stage_key: Int?,
    val stage_name: String?,
    val goalscorers: ArrayList<Goalscorer>?,
    //val substitutes: ArrayList<Substitute>?,
    val cards: ArrayList<Card>?,
    val lineups: Lineups?,
    val statistics: ArrayList<EventStatistics>?,
    var isHeader: Boolean = false,
    var isExpand: Boolean = false,
    var homeFormationLineups : ArrayList<ArrayList<StartingLineup>>? = null,
    var awayFormationLineups : ArrayList<ArrayList<StartingLineup>>? = null
) : Parcelable
