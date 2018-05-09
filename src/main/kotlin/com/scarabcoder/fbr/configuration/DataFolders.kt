package com.scarabcoder.fbr.configuration

import com.scarabcoder.fbr.fbrPlugin
import java.io.File

object DataFolders {

    val pluginFolder = fbrPlugin.dataFolder
    val arenasFolder = File(pluginFolder, "arenas")

    init {
        arenasFolder.mkdirs()
    }

}