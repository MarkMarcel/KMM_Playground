package com.stackconstruct.kmmplayground.android

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.libraries.maps.MapView

@Composable
fun GoogleMap(context: Context, onViewInflated: (view: MapView) -> Unit) =
    AndroidView(factory = { MapView(context) },update = onViewInflated)