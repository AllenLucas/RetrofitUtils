package com.allenlucas.retrofitutils

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class BaseViewModel : ViewModel() {

    private val apiManager: ApiManager = ApiManager.instance
    private val api: IApi = apiManager.create(IApi::class.java)

    fun getBanner() {
        viewModelScope.launch {
            val res = apiManager.callHandler { api.getBanner() }
            Log.e("lal", "code1:${res.code},msg1:${res.message},time:${System.currentTimeMillis()}")
            res.data?.forEach {
                Log.e("lal-->获取的banner遍历", "title:${it.title}")
            }
        }
    }

}