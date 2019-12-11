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

package com.jmzsoftware.jmzwirelessadb.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.Composable
import androidx.compose.Model
import androidx.compose.setViewContent
import androidx.ui.core.dp
import androidx.ui.layout.Column
import androidx.ui.layout.Padding
import androidx.ui.material.Button
import androidx.ui.material.ContainedButtonStyle
import androidx.ui.material.MaterialTheme
import androidx.ui.text.TextSpan
import androidx.ui.tooling.preview.Preview
import com.jmzsoftware.jmzwirelessadb.R
import com.jmzsoftware.jmzwirelessadb.util.NetworkUtils
import com.jmzsoftware.jmzwirelessadb.util.ShellCommands

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setViewContent {
            AdbUI {
                if (State.isEnabled) {
                    ShellCommands.disableAdb()
                } else {
                    ShellCommands.enableAdb()
                }
                State.isEnabled = ShellCommands.isAdbTcpEnabled()
            }
        }
    }

    @Composable
    fun AdbUI(onClick: () -> Unit = {}) {
        Column {
            Padding(bottom = 16.dp) {
                Button(resources.getString(if (State.isEnabled) R.string.disable else R.string.enable), style = ContainedButtonStyle(), onClick = onClick)
            }
            TextSpan(text = if (State.isEnabled) resources.getString(R.string.noti, NetworkUtils.ip) else "")
        }
    }

    @Model
    object State {
        var isEnabled = ShellCommands.isAdbTcpEnabled()
    }
}

@Preview
@Composable
fun DefaultUI() {
    MaterialTheme {
        Column {
            Padding(bottom = 16.dp) {
                Button(if (MainActivity.State.isEnabled) "Disable" else "Enable", style = ContainedButtonStyle())
            }
            TextSpan(text = if (MainActivity.State.isEnabled) "Run \'adb connect %s:5555\' in terminal" else "")
        }
    }
}
