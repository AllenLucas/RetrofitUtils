package com.allenlucas.retrofitutils

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider

class MainActivity : AppCompatActivity() {

    val viewModel by lazy { ViewModelProvider(this)[BaseViewModel::class.java] }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ApiManager.instance.timeOptions.apply {
            readTimeout(5)
            connectTimeout(5)
            callTimeout(5)
            writeTimeout(5)
        }
        Log.e("lal", "start time:${System.currentTimeMillis()}")
        viewModel.getBanner()
    }
}