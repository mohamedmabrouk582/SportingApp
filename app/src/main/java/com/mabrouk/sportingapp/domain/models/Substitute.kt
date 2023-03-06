package com.mabrouk.sportingapp.domain.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

/**
 * @name Mohamed Mabrouk
 * Copyright (c) 2/20/23
 */
@Parcelize
data class Substitute(
    val time: String,
    val home_scorer: Scorer?,
    val home_assist: String?,
    val score: String,
    val away_scorer: Scorer?,
    val away_assist: String?,
    val info: String?,
    val info_time: String?
) : Parcelable

@Parcelize
data class Scorer(
    @SerializedName("in")
    val scorer_in: String,
    @SerializedName("out")
    val scorer_out: String,
    val in_id: Long,
    val out_id: Long
) : Parcelable


