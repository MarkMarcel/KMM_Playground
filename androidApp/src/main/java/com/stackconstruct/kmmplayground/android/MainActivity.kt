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
import com.stackconstruct.kmmplayground.Greeting
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

data class OSAndVersion(val os: String, val version: String)


fun greet(): String {
    return Greeting().greeting()
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
            addMapFragment()
        }
    }

    private fun addMapFragment() {
        val mapView = SupportMapFragment.newInstance()
        supportFragmentManager
            .beginTransaction()
            .add(mapView, "map")
            .commitNow()
        mapView.getMapAsync(MapState)
    }

}
