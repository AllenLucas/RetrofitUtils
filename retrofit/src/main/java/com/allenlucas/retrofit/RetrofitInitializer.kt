package com.allenlucas.retrofit

import android.content.Context
import androidx.startup.Initializer

/**
 * εΊεε§ε
 */
class RetrofitInitializer : Initializer<Unit> {
    override fun create(context: Context) {
        ContextHelper.instance.mContext = context
    }

    override fun dependencies(): MutableList<Class<out Initializer<*>>> = arrayListOf()
}