package com.mabrouk.sportingapp.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * @name Mohamed Mabrouk
 * Copyright (c) 2/20/23
 */
@Parcelize
data class Lineups(
    val home_team: Team,
    val away_team: Team
) : Parcelable

@Parcelize
data class Team(
    val starting_lineups: ArrayList<StartingLineup>,
    val substitutes: ArrayList<StartingLineup>,
    val coaches: ArrayList<Coach>
) : Parcelable

@Parcelize
data class StartingLineup(
    val player: String,
    val player_number: Int,
    val player_position: Int,
    val player_country: String?,
    val player_key: Long,
    val info_time: String?
) : Parcelable

@Parcelize
data class Coach(
    val coache: String,
    val coache_country: String?
) : Parcelable
