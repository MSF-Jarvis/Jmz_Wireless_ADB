package com.jmzsoftware.jmzwirelessadb.services

import android.content.Context
import android.net.wifi.WifiManager
import android.os.Build
import android.service.quicksettings.Tile
import android.service.quicksettings.TileService
import android.widget.Toast
import com.jmzsoftware.jmzwirelessadb.R
import com.jmzsoftware.jmzwirelessadb.util.ShellCommands

class QuickSettingTileService : TileService() {

    private val wifiManager: WifiManager by lazy { getSystemService(Context.WIFI_SERVICE) as WifiManager }

    override fun onStartListening() {
        qsTile.updateStateAndSubtitle()
    }

    override fun onClick() {
        if (!ShellCommands.isRootAvailable()) {
            Toast.makeText(this, getString(R.string.root_not_available), Toast.LENGTH_SHORT).show()
            return
        }
        with(qsTile) {
            if (state == Tile.STATE_ACTIVE) {
                ShellCommands.disableAdb()
            } else {
                ShellCommands.enableAdb()
            }
            updateStateAndSubtitle()
        }
    }

    private fun Tile.updateStateAndSubtitle() {
        val isEnabled = ShellCommands.isAdbTcpEnabled()

        state = if (isEnabled) {
            Tile.STATE_ACTIVE
        } else {
            Tile.STATE_INACTIVE
        }

        label = if (isEnabled) {
            if (Build.VERSION.SDK_INT >= 29)
                getString(R.string.disable)
            else
                getIp()
        } else {
            getString(R.string.enable)
        }

        if (Build.VERSION.SDK_INT >= 29) {
            subtitle = if (isEnabled) {
                getIp()
            } else {
                ""
            }
        }
        updateTile()
    }

    private fun getIp(): String {
        val ip = wifiManager.connectionInfo.ipAddress
        return ((ip and 0xFF).toString() + "." + (ip shr 8 and 0xFF) + "." + (ip shr 16 and 0xFF) + "."
                + (ip shr 24 and 0xFF))
    }
}
