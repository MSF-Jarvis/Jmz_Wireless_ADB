/**
 * Jmz Wireless ADB
 *
 * Copyright 2016 by Jmz Software <support></support>@jmzsoftware.com>
 *
 *
 * Some open source application is free software: you can redistribute
 * it and/or modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation, either
 * version 3 of the License, or (at your option) any later version.
 *
 * Some open source application is distributed in the hope that it will
 * be useful, but WITHOUT ANY WARRANTY; without even the implied warranty
 * of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this code.  If not, see https://www.gnu.org/licenses/gpl-3.0.en.html
 *
 */

package com.jmzsoftware.jmzwirelessadb

import android.content.Context
import android.net.wifi.WifiManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import eu.chainfire.libsuperuser.Shell
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private val ip: String
        get() {
            val mWifiManager = applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
            val ip = mWifiManager.connectionInfo.ipAddress
            return ((ip and 0xFF).toString() + "." + (ip shr 8 and 0xFF) + "." + (ip shr 16 and 0xFF) + "."
                    + (ip shr 24 and 0xFF))

        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        button.setOnClickListener {
            if (button.text == resources.getString(R.string.disable)) {
                ShellCommands.disableAdb()
                textView.text = ""
                button.text = resources.getString(R.string.enable)
            } else {
                ShellCommands.enableAdb()
                textView.text = resources.getString(R.string.noti, ip)
                button.text = resources.getString(R.string.disable)
            }
        }
    }
}
