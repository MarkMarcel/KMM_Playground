package com.stackconstruct.kmmplayground.android

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
/*
            LocalLifecycleOwner.current.lifecycle.addObserver(observer)*/
//when updating a polyvector - add update type to points (new,modified,deleted)
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
