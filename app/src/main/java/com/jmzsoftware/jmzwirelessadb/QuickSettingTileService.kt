package com.jmzsoftware.jmzwirelessadb

import android.annotation.TargetApi
import android.os.Build
import android.service.quicksettings.Tile
import android.service.quicksettings.TileService

@TargetApi(24)
class QuickSettingTileService : TileService() {

    override fun onStartListening() {
        qsTile.updateStateAndSubtitle()
    }

    override fun onClick() {
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
            getString(R.string.disable)
        } else {
            getString(R.string.enable)
        }

        if (Build.VERSION.SDK_INT >= 29) {
            subtitle = if (isEnabled) {
                NetworkUtils.ip
            } else {
                ""
            }
        }

        updateTile()
    }
}
