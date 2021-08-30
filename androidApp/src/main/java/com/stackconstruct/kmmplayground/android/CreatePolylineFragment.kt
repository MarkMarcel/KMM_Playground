package com.stackconstruct.kmmplayground.android

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.lifecycle.HasDefaultViewModelProviderFactory
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import com.google.android.libraries.maps.MapView
import com.google.android.libraries.maps.model.Polyline

class CreatePolylineFragment : Fragment(), HasDefaultViewModelProviderFactory {
    private lateinit var googleMapsService: GoogleMapsService
    private lateinit var polyline: Polyline
    private val viewModel =
        ViewModelProvider(this).get(
            CreatePolylineViewModel::class.java)

    override fun getDefaultViewModelProviderFactory(): ViewModelProvider.Factory {
        val folder = mapOf(
            "title" to "Default Folder"
        )
        return CreatePolylineViewModel.CreatePolylineViewModelFactory(folder)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                Box {
                    val owner = LocalLifecycleOwner.current
                    GoogleMap(context = requireContext()) {
                        createGoogleMapsService(owner, Bundle(), it)
                    }
                    Column(modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(text = "Move the map around to set the position of the current point" +
                                ", - to remove current point click + to add another point, and" +
                                " switch points with < and >. Click save to create polyline",
                            modifier = Modifier
                                .background(Color.Black)
                                .padding(16.dp),
                            color = Color.White)
                        Spacer(modifier = Modifier.height(8.dp))
                        Card {
                            Row(modifier = Modifier.padding(16.dp)) {
                                Icon(painter = painterResource(id = R.drawable.ic_baseline_folder_24),
                                    contentDescription = "Select folder")
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(text = "Default Folder")
                            }
                        }
                    }
                    Column(horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .padding(16.dp)
                            .align(
                                Alignment.BottomStart)) {
                        Card {
                            Row(horizontalArrangement = Arrangement.SpaceBetween,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp)) {
                                Icon(painter = painterResource(id = R.drawable.ic_baseline_navigate_before_24),
                                    contentDescription = "Previous point",
                                    modifier = Modifier.size(36.dp))
                                Icon(painter = painterResource(id = R.drawable.ic_baseline_add_24),
                                    contentDescription = "Add point",
                                    modifier = Modifier.size(36.dp))
                                Icon(painter = painterResource(id = R.drawable.ic_baseline_remove_24),
                                    contentDescription = "Remove point",
                                    modifier = Modifier.size(36.dp))
                                Icon(painter = painterResource(id = R.drawable.ic_baseline_navigate_next_24),
                                    contentDescription = "Next point",
                                    modifier = Modifier.size(36.dp))
                                Icon(painter = painterResource(id = R.drawable.ic_baseline_done_24),
                                    contentDescription = "Done", modifier = Modifier.size(36.dp))
                            }
                        }
                    }
                }
            }
        }
    }

    private fun createGoogleMapsService(
        lifeCycleOwner: LifecycleOwner,
        savedInstanceState: Bundle?,
        view: MapView,
    ) {
        googleMapsService = GoogleMapsService(lifeCycleOwner, view)
        googleMapsService.onCreate(savedInstanceState)
    }

    private fun observeMapCenterChanges(){
        googleMapsService.mapCenter.observe(this){
            viewModel.onCurrentPointChanged(it)
        }
    }

    private fun observePolylineChanges()
}