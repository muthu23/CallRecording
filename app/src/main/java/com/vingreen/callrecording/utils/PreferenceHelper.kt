package com.vingreen.callrecording.utils

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager

class PreferenceHelper() {

    internal lateinit var mPref: SharedPreferences

    constructor(context: Context) : this() {
        mPref = PreferenceManager.getDefaultSharedPreferences(context)
    }

    constructor(context: Context, fileName: String) : this() {
        mPref = context.getSharedPreferences(fileName, Context.MODE_PRIVATE)
    }

}

fun PreferenceHelper.clearAll() {
    mPref.edit().clear().apply()
}

fun PreferenceHelper.setValue(key: String, value: Any?) {
    val editor = mPref.edit()
    when (value) {
        is String? -> editor.putString(key, value)
        is Int -> editor.putInt(key, value)
        is Boolean -> editor.putBoolean(key, value)
        is Float -> editor.putFloat(key, value)
        is Long -> editor.putLong(key, value)
        else -> throw UnsupportedOperationException("Not yet implemented")
    }
    editor.apply()
}


fun PreferenceHelper.getValue(key: String, defaultValue: Any): Any? {
    return when (defaultValue) {
        is String -> mPref.getString(key, defaultValue)
        is Int -> mPref.getInt(key, defaultValue)
        is Boolean -> mPref.getBoolean(key, defaultValue)
        is Float -> mPref.getFloat(key, defaultValue)
        is Long -> mPref.getLong(key, defaultValue)
        else -> throw UnsupportedOperationException("Not yet implemented")
    }
}