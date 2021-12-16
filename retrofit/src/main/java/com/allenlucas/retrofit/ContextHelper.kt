package com.allenlucas.retrofit

import android.content.Context
import kotlin.properties.Delegates

/**
 * context的管理类,初始化时赋值
 */
class ContextHelper private constructor() {

    companion object {
        val instance by lazy { ContextHelper() }
    }

    var mContext by Delegates.notNull<Context>()

    fun getAppContext() = mContext.applicationContext

}