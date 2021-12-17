package com.allenlucas.retrofitutils

import com.allenlucas.retrofitutils.entity.Banner
import com.allenlucas.retrofitutils.entity.ResponseResult
import retrofit2.http.GET

interface IApi {

    @GET("banner/json")
    suspend fun getBanner(): ResponseResult<List<Banner>>
}