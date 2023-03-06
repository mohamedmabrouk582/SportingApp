package com.mabrouk.sportingapp.persentaion.view

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

/**
 * @name Mohamed Mabrouk
 * Copyright (c) 3/3/23
 */

@Composable
fun showMap(location : LatLng){
    val cameraPositionSate = rememberCameraPositionState{
        position = CameraPosition.fromLatLngZoom(location, 17f)
    }

    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = cameraPositionSate
    ){
        Marker(
            state = MarkerState(location),
            title = "me" ,
            tag = location
        )

    }
}

@Preview(showBackground = true)
@Composable
fun PreviewMap(){
    showMap(location = LatLng(25.1183734,55.391936))
}