package com.jmzsoftware.jmzwirelessadb.util

import eu.chainfire.libsuperuser.Shell

object ShellCommands {

    private const val ADB_TCP_PORT_SYSTEM_PROP = "service.adb.tcp.port"
    private const val ADB_PORT = "5555"

    fun isRootAvailable() = Shell.SU.available()

    fun enableAdb() {
        runCommands(arrayOf("setprop $ADB_TCP_PORT_SYSTEM_PROP $ADB_PORT", "stop adbd", "start adbd"))
    }

    fun disableAdb() {
        runCommands(arrayOf("setprop $ADB_TCP_PORT_SYSTEM_PROP 0", "stop adbd", "start adbd"))
    }

    fun isAdbTcpEnabled() = try {
        val command = "getprop $ADB_TCP_PORT_SYSTEM_PROP"
        val stdout = mutableListOf<String>()
        Shell.Pool.SH.run(arrayOf(command), stdout, mutableListOf<String>(), true)
        stdout.any { it.trim() == ADB_PORT }
    } catch (ignored: Shell.ShellDiedException) {
        false
    }

    private fun runCommands(cmds: Array<String>) {
        try {
            Shell.Pool.SU.run(cmds)
        } catch (ignored: Shell.ShellDiedException) {
        }
    }
}
