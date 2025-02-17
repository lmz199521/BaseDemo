package com.example.appUtils

import android.app.Application

/**
 * describe:
 * Date:2025/1/1
 * Author:lmz
 */
object AppUtils {
    private lateinit var mContext: Application

    fun init(context: Application) {
        mContext = context
    }

    fun getContext(): Application {
        return mContext
    }
}