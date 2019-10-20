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
import com.jmzsoftware.jmzwirelessadb.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        updateState()
        binding.button.setOnClickListener {
            if (binding.button.text == resources.getString(R.string.disable)) {
                ShellCommands.disableAdb()
            } else {
                ShellCommands.enableAdb()
            }
            updateState()
        }
    }

    private fun updateState() {
        if (ShellCommands.isAdbTcpEnabled()) {
            binding.textView.text = resources.getString(R.string.noti, NetworkUtils.ip)
            binding.button.text = resources.getString(R.string.disable)
        } else {
            binding.textView.text = ""
            binding.button.text = resources.getString(R.string.enable)
        }
    }
}
