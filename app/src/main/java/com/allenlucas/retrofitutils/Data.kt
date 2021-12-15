package com.allenlucas.retrofitutils

import com.allenlucas.retrofit.BaseResult

data class Banner(
    var id: Long = 0,
    var title: String = "",
    var dec: String = "",
    var url: String = ""
)

data class ResponseResult<T>(
    var errorCode: Int = -1,
    var errorMsg: String? = ""
) : BaseResult<T>() {
    override fun isSuccess() = errorCode == 0

    override fun getRequestData() = data

    override fun getRequestMsg() = errorMsg ?: ""
}