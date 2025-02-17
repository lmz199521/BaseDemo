package com.example.extension

import androidx.activity.ComponentActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.demo.http.RetrofitUtils
import com.lmz.network.http.IHttpRequestService
import com.lmz.network.http.ResponseBean
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

fun <T : IHttpRequestService> getService(clazz: Class<T>): T {
    val create = RetrofitUtils.create(clazz)
    return create
}

fun <T> ComponentActivity.loadHttp(
    request: suspend CoroutineScope.() -> ResponseBean<T>,
    onStart: () -> Unit = {},
    onFail: (code: Int, msg: String) -> Unit = { code, msg -> },
    onSuccess: (T?) -> Unit,
    onError: (Exception) -> Unit = { },
    oComplete: () -> Unit = {}
) {
    lifecycleScope.launch {
        try {
            onStart()
            val data = request()
            if (data.code == 200) {
                onSuccess(data.data)
            } else {
                onFail(data.code, data.msg)
            }
        } catch (e: Exception) {
            onError(e)
        } finally {
            oComplete()
        }
    }
}

fun <T> Fragment.loadHttp(
    request: suspend CoroutineScope.() -> ResponseBean<T>,
    onStart: () -> Unit = {},
    onFail: (code: Int, msg: String) -> Unit = { code, msg -> },
    onSuccess: (T?) -> Unit,
    onError: (Exception) -> Unit = { },
    oComplete: () -> Unit = {}
) {
    lifecycleScope.launch {
        try {
            onStart()
            val data = request()
            if (data.code == 200) {
                onSuccess(data.data)
            } else {
                onFail(data.code, data.msg)
            }
        } catch (e: Exception) {
            onError(e)
        } finally {
            oComplete()
        }
    }
}

fun <T> CoroutineScope.loadHttp(
    request: suspend CoroutineScope.() -> ResponseBean<T>,
    onStart: () -> Unit = {},
    onFail: (code: Int, msg: String) -> Unit = { code, msg -> },
    onSuccess: (T?) -> Unit,
    onError: (Exception) -> Unit = { },
    oComplete: () -> Unit = {}
) {
    launch {
        try {
            onStart()
            val data = request()
            if (data.code == 200) {
                onSuccess(data.data)
            } else {
                onFail(data.code, data.msg)
            }
        } catch (e: Exception) {
            onError(e)
        } finally {
            oComplete()
        }
    }
}

