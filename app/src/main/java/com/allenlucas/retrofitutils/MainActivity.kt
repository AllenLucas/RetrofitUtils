package com.allenlucas.retrofitutils

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider

class MainActivity : AppCompatActivity() {

    val viewModel: BaseViewModel by lazy { ViewModelProvider(this)[BaseViewModel::class.java] }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ApiManager.instance.initOkHttpClient()
        findViewById<View>(R.id.tv_content).setOnClickListener {
            Log.e("lal", "start time:${System.currentTimeMillis()}")
            viewModel.getBanner()
        }
    }
}