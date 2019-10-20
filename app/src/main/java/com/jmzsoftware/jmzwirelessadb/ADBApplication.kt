package com.jmzsoftware.jmzwirelessadb

import android.app.Application

class ADBApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
    }

    companion object {
        lateinit var INSTANCE: Application
    }
}
