package com.allenlucas.retrofitutils

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.allenlucas.retrofit.ContextHelper
import com.allenlucas.retrofitutils.entity.ResponseResult
import kotlinx.coroutines.launch

class BaseViewModel : ViewModel() {

    private val apiManager by lazy { ApiManager.instance }
    private val api by lazy { apiManager.create(IApi::class.java) }

    fun getBanner() {
        viewModelScope.launch {
            val res = apiManager.callHandler(ResponseResult()) { api.getBanner() }
            Toast.makeText(ContextHelper.instance.getAppContext(),"code:${res.errorCode},msg:${res.errorMsg}",Toast.LENGTH_SHORT).show()
            Log.e("lal", "code:${res.errorCode},msg:${res.errorMsg}")
            Log.e("lal","code1:${res.code},msg1:${res.msg},time:${System.currentTimeMillis()}")
            res.data?.forEach {
                Log.e("lal-->获取的banner遍历", "title:${it.title}")
            }
        }
    }

}