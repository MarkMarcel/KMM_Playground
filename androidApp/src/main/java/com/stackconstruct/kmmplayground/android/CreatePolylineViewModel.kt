package com.stackconstruct.kmmplayground.android

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.libraries.maps.model.LatLng

class CreatePolylineViewModel(initialFolder:Map<String,String>):ViewModel() {
    val addedPoints = MutableLiveData<List<LatLng>>(emptyList())
    var nextEnabled = false
        private set
    var previousEnabled = false
        private set
    var removePointEnabled = false
        private set
    val selectedFolder = MutableLiveData<Map<String,String>>()
    private var currentIndex = 0
    var currentPoint:LatLng? = null

    init{
        selectedFolder.value = initialFolder
    }

    fun onCurrentPointChanged(newPosition:LatLng) {
        currentPoint = newPosition
        val points = addedPoints.value!!.toMutableList()
        points[currentIndex] = currentPoint!!
        addedPoints.value = points.toList()
    }

    fun onRemovePoint(){
        val points = addedPoints.value!!.toMutableList()
        points.removeAt(currentIndex)
        currentIndex--
        setRemovePointEnabled()
    }

    fun onSelectedFolderChanged(newFolder:Map<String,String>){
        selectedFolder.value = newFolder
    }

    private fun setRemovePointEnabled(){
        removePointEnabled = currentIndex > 0
    }
}