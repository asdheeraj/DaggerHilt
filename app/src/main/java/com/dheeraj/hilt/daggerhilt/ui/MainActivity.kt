package com.dheeraj.hilt.daggerhilt.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dheeraj.hilt.daggerhilt.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}