package com.mabrouk.sportingapp.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * @name Mohamed Mabrouk
 * Copyright (c) 2/20/23
 */
@Parcelize
data class EventStatistics(
    val type: String,
    val home: String,
    val away: String
) : Parcelable
