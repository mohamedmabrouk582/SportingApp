package com.mabrouk.sportingapp.persentaion.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.google.android.gms.maps.model.LatLng
import com.mabrouk.sportingapp.domain.models.Event

/**
 * @name Mohamed Mabrouk
 * Copyright (c) 2/28/23
 */

@Composable
fun eventDetailScreen(event: Event){
    showMap(location = LatLng(25.1183734,55.391936))
}


