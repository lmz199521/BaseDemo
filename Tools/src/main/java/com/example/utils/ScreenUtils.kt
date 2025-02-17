package com.example.Tools

import android.content.Context
import android.content.res.Resources
import android.os.Build
import android.view.View
import android.view.WindowManager
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat

/**
 * describe: 屏幕相关工具类
 * Date:2024/12/11
 * Author:lmz
 */
object ScreenUtils {

    /**
     *  获取屏幕的宽度
     */
    @JvmStatic
    fun getScreenWidth(context: Context): Int {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {

            return context.display?.width ?: 0
        } else {
            val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
            return wm.defaultDisplay.width
        }
    }

    /**
     *  获取屏幕的高度
     */
    @JvmStatic
    fun getScreenHeight(context: Context): Int {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            return context.display?.height ?: 0
        } else {
            val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
            return wm.defaultDisplay.height
        }
    }


    /**
     *  获取导航栏的高度
     */
    @JvmStatic
    fun getNavigationBarHeight(view: View, callback: (Int) -> Unit) {
        ViewCompat.setOnApplyWindowInsetsListener(view) { _, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.navigationBars())
            val navBarHeight = systemBars.bottom
            callback(navBarHeight)
            insets
        }
        // 触发一次窗口内边距应用
        ViewCompat.requestApplyInsets(view)
    }

    /**
     *  获取状态栏的高度
     */
    @JvmStatic
    fun getStatusBarHeight(view: View, callback: (Int) -> Unit) {
        ViewCompat.setOnApplyWindowInsetsListener(view) { _, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.statusBars())
            val statusBar = systemBars.top
            callback(statusBar)
            insets
        }
        // 触发一次窗口内边距应用
        ViewCompat.requestApplyInsets(view)
    }


    @JvmStatic
    fun dp2px(dpValue: Float): Float {
        val scale = Resources.getSystem().displayMetrics.density
        return dpValue * scale + 0.5f
    }

    @JvmStatic
    fun px2dp(pxValue: Float): Int {
        val scale = Resources.getSystem().displayMetrics.density
        return (pxValue / scale + 0.5f).toInt()
    }
}