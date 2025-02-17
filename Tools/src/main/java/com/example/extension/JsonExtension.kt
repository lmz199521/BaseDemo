package com.example.demo.extension

import com.example.demo.utils.GsonUtils

fun Any.toJsonString(): String {
    return GsonUtils.toJsonString(this)
}

inline fun <reified T> String.jsonToBean(): T {
    return GsonUtils.jsonToBean(this, T::class.java)
}

inline fun <reified T> String.jsonToList(): List<T> {
    return GsonUtils.jsonToList(this, T::class.java)
}

fun <T> String.jsonToListMaps(): List<Map<String, T>?> {
    return GsonUtils.jsonToListMaps<T>(this)
}

fun <T> String.jsonToMaps(): Map<String, T> {
    return GsonUtils.jsonToMaps<T>(this)
}