@file:Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")

package com.vingreen.callrecording.utils

object Constant {
    const val M_TOKEN = "Bearer "
    const val CUSTOM_PREFERENCE: String = "BaseConfigSetting"


    const val THEME = "theme"
    const val THEME_LIGHT = "light"
    const val THEME_DARK = "dark"
    const val THEME_DEFAULT = "default"

    object ROOM_NAME {
        var COMMON_ROOM_NAME: String = "joinCommonRoom"
        var STATUS: String = "socketStatus"
        var ON_MESSAGE_RECEIVE: String = "new_message"
    }

}

