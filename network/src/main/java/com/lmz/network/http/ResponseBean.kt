package com.lmz.network.http

abstract class ResponseBean<T> {
    var code: Int = 0
    var msg = ""
    var data: T? = null
}