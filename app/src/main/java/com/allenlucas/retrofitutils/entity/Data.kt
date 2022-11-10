package com.allenlucas.retrofitutils.entity

import com.allenlucas.retrofit.ResultData
import com.google.gson.annotations.SerializedName

data class Banner(
    var id: Long = 0,
    var title: String = "",
    var dec: String = "",
    var url: String = ""
)

data class ResponseResult<T>(
    @SerializedName("errorCode") override var code:Int,
    @SerializedName("errorMsg") override var message: String,
    @SerializedName("data") override var data: T?
):ResultData<T> {
    override fun isSuccess(): Boolean = code == 0
}