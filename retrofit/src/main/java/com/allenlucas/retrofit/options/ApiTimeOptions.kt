package com.allenlucas.retrofit.options

import java.util.concurrent.TimeUnit

/**
 * 网络请求超时配置
 */
class ApiTimeOptions {

    companion object {
        private const val TIME_OUT: Long = 10
        private val TIME_UNIT = TimeUnit.SECONDS
    }

    var timeOutOptions = RequestTimeOutOptions.Default

    // 请求超时配置
    data class RequestTimeOutOptions(
        var readTimeOut: Long = TIME_OUT, var readTimeUnit: TimeUnit = TIME_UNIT,
        var connectTimeOut: Long = TIME_OUT, var connectTimeUnit: TimeUnit = TIME_UNIT,
        var callTimeOut: Long = TIME_OUT, var callTimeUnit: TimeUnit = TIME_UNIT,
        var writeTimeOut: Long = TIME_OUT, var writeTimeUnit: TimeUnit = TIME_UNIT
    ) {
        companion object {
            var Default = RequestTimeOutOptions()

            inline fun setDefault(options: RequestTimeOutOptions.() -> Unit) {
                Default = Default.also(options)
            }
        }
    }

    fun readTimeout(timeout: Long, timeUnit: TimeUnit = TIME_UNIT) {
        setTimeOut {
            readTimeOut = timeout
            readTimeUnit = timeUnit
        }
    }

    fun connectTimeout(timeout: Long, timeUnit: TimeUnit = TIME_UNIT) {
        setTimeOut {
            connectTimeOut = timeout
            connectTimeUnit = timeUnit
        }
    }

    fun callTimeout(timeout: Long, timeUnit: TimeUnit = TIME_UNIT) {
        setTimeOut {
            callTimeOut = timeout
            callTimeUnit = timeUnit
        }
    }

    fun writeTimeout(timeout: Long, timeUnit: TimeUnit = TIME_UNIT) {
        setTimeOut {
            writeTimeOut = timeout
            writeTimeUnit = timeUnit
        }
    }

    inline fun setTimeOut(options: RequestTimeOutOptions.() -> Unit) {
        timeOutOptions = timeOutOptions.copy().apply(options)
    }
}