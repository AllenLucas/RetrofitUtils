package com.allenlucas.retrofit

import android.content.Context
import androidx.startup.Initializer

/**
 * 库初始化
 */
class RetrofitInitializer : Initializer<Unit> {
    override fun create(context: Context) {
        ContextHelper.instance.mContext = context
    }

    override fun dependencies(): MutableList<Class<out Initializer<*>>> = arrayListOf()
}