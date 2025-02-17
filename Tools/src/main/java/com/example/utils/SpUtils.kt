package com.example.demo.utils

import android.content.Context
import android.content.SharedPreferences
import android.content.SharedPreferences.Editor

class SpUtils {

    companion object {
        val instance by lazy { SpUtils() }
    }

    private lateinit var sp: SharedPreferences
    private lateinit var edit: Editor

    fun init(context: Context, spName: String) {
        sp = context.getSharedPreferences(spName, Context.MODE_PRIVATE)
        edit = sp.edit()
    }

    fun putData(key: String, value: String) {
        edit.putString(key, value).apply()
    }

    fun putData(key: String, value: Int) {
        edit.putInt(key, value).apply()
    }

    fun putData(key: String, value: Boolean) {
        edit.putBoolean(key, value).apply()
    }

    fun putData(key: String, value: Long) {
        edit.putLong(key, value).apply()
    }

    fun putData(key: String, value: Float) {
        edit.putFloat(key, value).apply()
    }

    fun putData(key: String, value: MutableSet<String>) {
        edit.putStringSet(key, value).apply()
    }

    fun getData(key: String, defaultValue: String = ""): String {
        return sp.getString(key, defaultValue) ?: defaultValue
    }

    fun getData(key: String, defaultValue: Int = -1): Int {
        return sp.getInt(key, defaultValue)
    }

    fun getData(key: String, defaultValue: Boolean = false): Boolean {
        return sp.getBoolean(key, defaultValue)
    }

    fun getData(key: String, defaultValue: Long = -1): Long {
        return sp.getLong(key, defaultValue)
    }

    fun getData(key: String, defaultValue: Float = -1f): Float {
        return sp.getFloat(key, defaultValue)
    }

    fun getData(key: String, defaultValue: MutableSet<String> = mutableSetOf()): MutableSet<String> {
        return sp.getStringSet(key, defaultValue) ?: defaultValue
    }

}