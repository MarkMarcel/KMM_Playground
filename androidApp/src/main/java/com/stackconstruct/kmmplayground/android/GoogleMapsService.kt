package com.stackconstruct.kmmplayground.android

import android.os.Bundle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.libraries.maps.GoogleMap
import com.google.android.libraries.maps.MapView
import com.google.android.libraries.maps.model.LatLng
import com.google.android.libraries.maps.model.Polyline
import com.google.android.libraries.maps.model.PolylineOptions
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel

/**
 * [mapState] must be saved by user of service and passed in [onCreate] to restore map to previous
 * state
 * CONCERNS OF THIS CLASS
 * 1. Saving state across recomposition
 * 2. Communicating changes in map center position
 * 3. Creating and disposing coroutine scope
 * */

class GoogleMapsService(private val lifecycleOwner: LifecycleOwner, private val view: MapView) {
    val mapCenter:LiveData<LatLng>
        get() = _mapCenter
    lateinit var mapState: Bundle
        private set

    private lateinit var map: GoogleMap
    private val _mapCenter = MutableLiveData<LatLng>()
    private val serviceScope = MainScope()

    fun addPolyline(polylineOptions:PolylineOptions):Polyline{
        return map.addPolyline(polylineOptions)
    }

    fun onCreate(savedInstanceState: Bundle?) {
        mapState = savedInstanceState ?: Bundle()
        view.onCreate(mapState)
        getMap()
    }

    fun onDestroy(){
        serviceScope.cancel()
    }

    private fun getMap(){
        view.getMapAsync {
            map = it
            configureMapUI()
            observeMapCenterChanges()
        }
    }

    private fun configureMapUI(){
        map.uiSettings.setAllGesturesEnabled(true)
    }

    private fun observeMapCenterChanges(){
        map.setOnCameraMoveListener {
            _mapCenter.value = map.cameraPosition.target
        }
    }
}