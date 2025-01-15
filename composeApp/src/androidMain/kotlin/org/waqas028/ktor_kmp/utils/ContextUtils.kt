package org.waqas028.ktor_kmp.utils

import android.content.Context

object ContextUtils {

    var dataStoreApplicationContext: Context? = null

    fun setContext(context: Context) {
        dataStoreApplicationContext = context.applicationContext
    }
}