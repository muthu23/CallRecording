package com.vingreen.callrecording.utils

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.content.Context
import android.content.res.Configuration
import android.os.Build
import android.preference.PreferenceManager
import java.util.*


class LocaleUtils {

    object LANGUAGE {
        const val KEY = "language_key"
        const val DEFAULT = "en"
    }

    companion object {
        fun setLocale(mContext: Context): Context {
            return updateResources(mContext, getLanguagePref(mContext).toString())
        }

        fun setNewLocale(mContext: Context, locale: String): Context {
            setLanguagePref(mContext, locale)
            return updateResources(mContext, locale)
        }


        @SuppressLint("NewApi")
        @SuppressWarnings("deprecation")
        private fun updateResources(context: Context, language: String): Context {
            val locale = Locale(language)
            Locale.setDefault(locale)
            val resources = context.resources
            val configuration = resources.configuration
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                setSystemLocale(configuration, locale)
            } else {
                setSystemLocaleLegacy(configuration, locale)
            }
            configuration.setLayoutDirection(locale)
            return context.createConfigurationContext(configuration)
        }

        @SuppressWarnings("deprecation")
        fun setSystemLocaleLegacy(config: Configuration, locale: Locale) {
            config.locale = locale
        }

        @TargetApi(Build.VERSION_CODES.N)
        fun setSystemLocale(config: Configuration, locale: Locale) {
            config.setLocale(locale)
        }

        fun getLanguagePref(mContext: Context): String? {
            val mPreferences = PreferenceManager.getDefaultSharedPreferences(mContext)
            return mPreferences.getString(LANGUAGE.KEY, LANGUAGE.DEFAULT)
        }

        private fun setLanguagePref(mContext: Context, locale: String) {
            val mPreferences = PreferenceManager.getDefaultSharedPreferences(mContext)
            mPreferences.edit().putString(LANGUAGE.KEY, locale).apply()
        }
    }


}