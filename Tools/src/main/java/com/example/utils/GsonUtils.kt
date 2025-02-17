package com.example.demo.utils

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import com.google.gson.LongSerializationPolicy
import com.google.gson.reflect.TypeToken


object GsonUtils {

    private val gson: Gson by lazy {
        GsonBuilder()
            .setLongSerializationPolicy(LongSerializationPolicy.DEFAULT)
            .serializeNulls()
            .create()
    }

    fun toJsonString(obj: Any): String {
        return gson.toJson(obj)
    }

    fun <T> jsonToBean(text: String, any: Class<T>): T {
        return gson.fromJson(text, any)
    }

    fun <T> jsonToList(json: String, cls: Class<T>?): List<T> {
        val list: MutableList<T> = ArrayList()
        val array = JsonParser.parseString(json).asJsonArray
        for (elem in array) {
            list.add(gson.fromJson(elem, cls))
        }
        return list
    }


    fun <T> jsonToListMaps(gsonString: String): List<Map<String, T>?> {
        return gson.fromJson(gsonString, object : TypeToken<List<Map<String, T>?>?>() {}.type)
    }

    fun <T> jsonToMaps(gsonString: String): Map<String, T> {
        return gson.fromJson(gsonString, object : TypeToken<Map<String, T>?>() {}.type)
    }

}