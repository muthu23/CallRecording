package com.vingreen.callrecording

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.vingreen.callrecording.utils.Constant
import com.vingreen.callrecording.utils.ViewUtils.preferenceHelper
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class RecordApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        baseApplication = this
        preferences = getSharedPreferences(Constant.CUSTOM_PREFERENCE, MODE_PRIVATE)
        preferenceHelper
    }
    companion object {
        var baseApplication: Context? = null
        private lateinit var preferences: SharedPreferences

    }
}