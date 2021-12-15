package com.allenlucas.retrofit

import com.google.gson.JsonIOException
import com.google.gson.JsonParseException
import com.google.gson.JsonSyntaxException
import org.apache.http.conn.ConnectTimeoutException
import org.json.JSONException
import retrofit2.HttpException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class ApiException(
    val code: Int,
    override val message: String?,
    override val cause: Throwable? = null
) : RuntimeException(message, cause) {

    companion object {

        const val CODE_NET_ERROR = 4000
        const val CODE_TIMEOUT = 4001
        const val CODE_JSON_PARSE_ERROR = 4002
        const val CODE_SERVER_ERROR = 40003

        fun build(e: Throwable) = when (e) {
            is HttpException -> ApiException(CODE_NET_ERROR, "网络异常(${e.code()},${e.message()})")
            is UnknownHostException -> ApiException(CODE_NET_ERROR, "网络连接失败，请检查后再试")
            is ConnectTimeoutException, is SocketTimeoutException ->
                ApiException(CODE_TIMEOUT, "请求超时，请稍后再试")
            is JsonParseException, is JSONException, is JsonIOException, is JsonSyntaxException ->
                ApiException(CODE_JSON_PARSE_ERROR, "数据解析错误，请稍候再试")
            else -> ApiException(CODE_SERVER_ERROR, "系统错误(${e.message})")
        }
    }

    fun <T, R : BaseResult<T>> toBaseResult(emptyResult: R) =
        emptyResult.apply {
            this.code = this@ApiException.code
            this.msg = this@ApiException.message ?: ""
        }
}