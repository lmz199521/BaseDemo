package com.lmz.network.utils

import android.text.TextUtils
import android.util.Log
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

/**
 * describe:
 * Date:2024/12/18
 * Author:lmz
 */

private var IS_SHOW_LOG = true
private const val DEFAULT_MESSAGE = "execute"
private var LINE_SEPARATOR = System.lineSeparator()
private const val JSON_INDENT = 4

private const val V = 0x1
private const val D = 0x2
private const val I = 0x3
private const val W = 0x4
private const val E = 0x5
private const val A = 0x6
private const val JSON = 0x7

class KLog {

    companion object {
        @JvmStatic
        fun init(isShowLog: Boolean) {
            IS_SHOW_LOG = isShowLog
        }

        @JvmStatic
        fun v() {
            printLog(V, null, DEFAULT_MESSAGE)
        }

        @JvmStatic
        fun v(msg: String) {
            printLog(V, null, msg)
        }

        @JvmStatic
        fun v(tag: String, msg: String) {
            printLog(V, tag, msg)
        }

        @JvmStatic
        fun d() {
            printLog(D, null, DEFAULT_MESSAGE)
        }

        @JvmStatic
        fun d(msg: String) {
            printLog(D, null, msg)
        }

        @JvmStatic
        fun d(tag: String, msg: String) {
            printLog(D, tag, msg)
        }

        @JvmStatic
        fun i() {
            printLog(I, null, DEFAULT_MESSAGE)
        }

        @JvmStatic
        fun i(msg: String) {
            printLog(I, null, msg)
        }

        @JvmStatic
        fun i(tag: String, msg: String) {
            printLog(I, tag, msg)
        }

        @JvmStatic
        fun w() {
            printLog(W, null, DEFAULT_MESSAGE)
        }

        @JvmStatic
        fun w(msg: String) {
            printLog(W, null, msg)
        }

        @JvmStatic
        fun w(tag: String, msg: String) {
            printLog(W, tag, msg)
        }

        @JvmStatic
        fun e() {
            printLog(E, null, DEFAULT_MESSAGE)
        }

        @JvmStatic
        fun e(msg: String) {
            printLog(E, null, msg)
        }

        @JvmStatic
        fun e(tag: String, msg: String) {
            printLog(E, tag, msg)
        }

        @JvmStatic
        fun json(json: String) {
            printLog(JSON, null, json)
        }

        @JvmStatic
        fun json(tag: String, json: String) {
            printLog(JSON, tag, json)
        }

        @JvmStatic
        fun a() {
            printLog(A, null, DEFAULT_MESSAGE)
        }

        @JvmStatic
        fun a(msg: String) {
            printLog(A, null, msg)
        }

        @JvmStatic
        fun a(tag: String, msg: String) {
            printLog(A, tag, msg)
        }

        @JvmStatic
        private fun printLog(type: Int, tagStr: String?, msg: String?) {
            if (!IS_SHOW_LOG) return
            val stackTrace = Thread.currentThread().stackTrace as Array<StackTraceElement>
            val index = 4
            var className: String = ""
            var methodName: String?
            var lineNumber: Int = 0
            stackTrace[index].apply {
                className = fileName ?: "unknownClassName"
                methodName = this.methodName
                lineNumber = this.lineNumber
            }
            //  等价于  if(tagStr == null) className else tagStr
            val tag = tagStr ?: className
            methodName = methodName?.let {
                it.substring(0, 1).toUpperCase() + it.substring(1)
            }

            val sb = StringBuilder()
            sb.append("[ (").append(className).append(":").append(lineNumber).append(")#")
                .append(methodName).append(" ]")
            if (msg != null && type != JSON) {
                sb.append(msg)
            }

            val logStr = sb.toString()

            when (type) {
                V -> Log.v(tag, logStr)
                D -> Log.d(tag, logStr)
                I -> Log.i(tag, logStr)
                W -> Log.w(tag, logStr)
                E -> Log.e(tag, logStr)
                A -> Log.wtf(tag, logStr)
                JSON -> {
                    if (TextUtils.isEmpty(msg)) {
                        Log.d(tag, "empty or null json content")
                        return
                    }

                    var message: String? = null

                    try {
                        if (msg!!.startsWith('{')) {
                            val jsonObject = JSONObject(msg)
                            message = jsonObject.toString(JSON_INDENT)
                        } else if (msg.startsWith("}")) {
                            val jsonArray = JSONArray(msg)
                            message = jsonArray.toString(JSON_INDENT)
                        }
                    } catch (e: JSONException) {
                        Log.e(tag, e.cause?.message + "\n" + msg)
                        return
                    }

                    printLine(tag, true)
                    message = logStr + LINE_SEPARATOR + message
                    val lines = message.split(LINE_SEPARATOR)
                    val jsb = StringBuilder()
                    for (line in lines) {
                        jsb.append("||").append(line).append(LINE_SEPARATOR)
                    }
                    Log.d(tag, jsb.toString())
                    printLine(tag, false)
                }
            }


        }

        @JvmStatic
        private fun printLine(tag: String, isTop: Boolean) {
            if (isTop) {
                Log.d(
                    tag,
                    "╔═══════════════════════════════════════════════════════════════════════════════════════"
                )
            } else {
                Log.d(
                    tag,
                    "╚═══════════════════════════════════════════════════════════════════════════════════════"
                )
            }
        }
    }

}