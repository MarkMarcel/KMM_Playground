package com.stackconstruct.kmmplayground.android

import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.ViewGroup
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.fragment.app.FragmentContainerView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.google.android.libraries.maps.GoogleMap
import com.google.android.libraries.maps.MapView
import com.google.android.libraries.maps.OnMapReadyCallback
import com.google.android.libraries.maps.SupportMapFragment
import com.google.android.libraries.maps.model.*
import com.stackconstruct.kmmplayground.Greeting
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

data class OSAndVersion(val os: String, val version: String)


fun greet(): String {
    return Greeting().greeting()
}

object GoogleMapsService {
    //Keeping tract of host lifecycle
    private val accra = LatLng(5.6037, 0.1870)
    private val lome = LatLng(6.1256, 1.2254)
    private val cameraTarget: MutableStateFlow<LatLng?> = MutableStateFlow(null)
    private val coroutineScope = MainScope()
    private var marker: Marker? = null
    private lateinit var _map: GoogleMap
    private lateinit var _mapView: MapView
    private var polyline: Polyline? = null

    private object CameraMoveListener : GoogleMap.OnCameraMoveListener {
        override fun onCameraMove() {
            coroutineScope.launch { cameraTarget.emit(_map.cameraPosition.target) }
        }
    }

    fun onCreate(mapView: MapView, savedInstanceState: Bundle?) {
        _mapView = mapView
        initGoogleMap(savedInstanceState)
    }

    fun onDestroy() {
        coroutineScope.cancel()
        _mapView.onDestroy()
    }

    fun onPause(){
        _mapView.onPause()
    }

    private fun initGoogleMap(savedInstanceState: Bundle?) {
        _mapView.onCreate(savedInstanceState)
        _mapView.getMapAsync {
            _map = it
            _map.addMarker(MarkerOptions().position(accra))
            _map.setOnCameraMoveListener(CameraMoveListener)
            observeCameraTargetChanges()
        }
    }

    private fun observeCameraTargetChanges() {
        coroutineScope.launch {
            cameraTarget.collect {
                it?.let {
                    if (polyline != null) {
                        polyline!!.points = listOf(accra, lome, it)
                    } else {
                        val options = PolylineOptions().add(accra, lome, it)
                        polyline = _map.addPolyline(options).apply {
                            endCap =
                                CustomCap(BitmapDescriptorFactory.fromResource(R.drawable.ic_stat_name))
                        }
                    }

                    /* if(marker != null){
                         marker!!.position = it
                     }else{
                         marker = _map.addMarker(MarkerOptions().position(it))
                     }*/
                }
            }
        }
    }
}

//Do not forget handling lifecycle events during the use of a feature
//Create lifecycle aware components?
//when updating a polyvector - add update type to points (new,modified,deleted)
class MainActivity : AppCompatActivity() {

    private object MapState : OnMapReadyCallback {
        override fun onMapReady(map: GoogleMap?) {
            map?.uiSettings?.setAllGesturesEnabled(true)
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            setContentView(FragmentContainerView(this))
            addMapFragment(Bundle())
        }
    }

    override fun onDestroy() {
        GoogleMapsService.onDestroy()
        super.onDestroy()
    }

    override fun onPause() {
        GoogleMapsService.onPause()
        super.onPause()
    }

    private fun addMapFragment(savedInstanceState: Bundle?) {
        val mapView = MapView(this)
        GoogleMapsService.onCreate(mapView, savedInstanceState)
        setContentView(mapView)
    }

}
