package com.example.demo

import android.app.Application
import com.example.appUtils.AppActivityLifecycleCallbacksImpl
import com.example.demo.utils.SpUtils
import com.example.appUtils.AppUtils

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        registerActivityLifecycleCallbacks(AppActivityLifecycleCallbacksImpl())
        SpUtils.instance.init(this, ConstantUtils.SP_FILE_NAME)
        AppUtils.init(this)
    }

}