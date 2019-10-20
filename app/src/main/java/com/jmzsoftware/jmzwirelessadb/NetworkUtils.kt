package com.jmzsoftware.jmzwirelessadb

import android.content.Context
import android.net.wifi.WifiManager

object NetworkUtils {
    private val wifiManager by lazy { ADBApplication.INSTANCE.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager }

    val ip: String
        get() {
            val ip = wifiManager.connectionInfo.ipAddress
            return ((ip and 0xFF).toString() + "." + (ip shr 8 and 0xFF) + "." + (ip shr 16 and 0xFF) + "."
                    + (ip shr 24 and 0xFF))
        }
}
