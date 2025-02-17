package com.example.appUtils

import android.app.Activity
import android.util.Log
import java.util.Stack

/**
 * describe: 管理Activity
 * Date:2025/1/1
 * Author:lmz
 */
class ActivityManager {
    private val activityStack: Stack<Activity> = Stack<Activity>()

    fun addActivity(activity: Activity?) {
        activity?.let {
            activityStack.add(it)
        }
    }

    fun finishActivity(activity: Activity?) {
        activity?.let {
            activityStack.remove(it)
            if (!it.isFinishing) {
                it.finish()
            }
        }
    }

    fun removeActivity(activity: Activity?) {
        activity?.let {
            activityStack.remove(it)
        }
    }

    fun finishAllActivity() {
        activityStack.forEach {
            it.finish()
        }
        activityStack.clear()
    }

    fun finishAllButThis(cls: Class<*>) {
        finishAllButThis(cls.name)
    }

    fun finishAllButThis(className: String) {
        val it: MutableIterator<Activity> = activityStack.iterator()
        while (it.hasNext()) {
            val activity = it.next()
            if (activity.javaClass.name != className) {
                activity.finish()
                it.remove()
            }
        }
    }

    fun finishActivity(clsName: String) {
        val it: MutableIterator<Activity> = activityStack.iterator()
        while (it.hasNext()) {
            val activity = it.next()
            if (activity.javaClass.name == clsName) {
                activity.finish()
            }
        }
    }

    fun topActivity(): Activity? {
        if (activityStack.isEmpty()) {
            return null
        }
        try {
            var activity: Activity = activityStack.peek()
            while (activity.isFinishing || activity.isDestroyed) {
                activityStack.pop()
                activity = activityStack.peek()
            }
            return activity
        } catch (e: Exception) {
            Log.e(TAG, "topActivity e:$e")
        }
        return null
    }


    companion object {
        const val TAG = "ActivityManager"
        val Instances by lazy { ActivityManager() }
    }
}