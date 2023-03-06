package com.mabrouk.sportingapp.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * @name Mohamed Mabrouk
 * Copyright (c) 2/20/23
 */
@Parcelize
data class Goalscorer(
    val score: String,
    val time: String,
    val home_scorer: String?,
    val home_scorer_id: String?,
    val home_assist: String?,
    val home_assist_id: String?,
    val away_scorer: String?,
    val away_scorer_id: String?,
    val away_assist: String?,
    val away_assist_id: String?,
    val info: String,
    val info_time: String
) : Parcelable
