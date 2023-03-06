package com.mabrouk.sportingapp.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * @name Mohamed Mabrouk
 * Copyright (c) 2/20/23
 */
@Parcelize
data class Card(
    val time: String,
    val home_fault: String?,
    val card: String,
    val away_fault: String?,
    val info: String?,
    val home_player_id: String?,
    val away_player_id: String?,
    val info_time: String
) : Parcelable
