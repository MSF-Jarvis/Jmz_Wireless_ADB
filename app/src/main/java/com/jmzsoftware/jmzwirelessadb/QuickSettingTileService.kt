package com.jmzsoftware.jmzwirelessadb

import android.annotation.TargetApi
import android.os.Build
import android.service.quicksettings.Tile
import android.service.quicksettings.TileService

@TargetApi(Build.VERSION_CODES.N)
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

        subtitle = if (isEnabled) {
            getString(R.string.disable)
        } else {
            getString(R.string.enable)
        }

        updateTile()
    }
}
