package ru.appspoint.sdk.android

import android.annotation.SuppressLint
import android.content.Context

@SuppressLint("StaticFieldLeak")
object AppsPoint {
    lateinit var context: Context

    fun init(context: Context) {
        this.context = context
    }
}