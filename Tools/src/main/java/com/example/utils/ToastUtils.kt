package com.example.utils

import android.widget.Toast
import com.example.appUtils.AppUtils

/**
 * describe:
 * Date:2025/1/1
 * Author:lmz
 */
object ToastUtils {
    private var mToast: Toast? = null

    fun cancel() {
        mToast?.let {
            it.cancel()
            mToast = null
        }
    }

    @JvmStatic
    fun show(resId: Int) {
        show(AppUtils.getContext().getString(resId))
    }

    @JvmStatic
    fun show(text: String) {
        if (text.isEmpty() || text.isBlank()) return

        cancel()
        mToast = Toast.makeText(AppUtils.getContext(), text, Toast.LENGTH_SHORT)
        mToast?.show()
    }

}