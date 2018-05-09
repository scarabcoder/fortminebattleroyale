package com.scarabcoder.fbr.arena

import com.scarabcoder.commons.generateEmptyWorld
import com.scarabcoder.commons.register
import com.scarabcoder.commons.task
import com.scarabcoder.fbr.configuration.DataFolders
import com.scarabcoder.fbr.fbrPlugin
import org.bukkit.Bukkit
import org.bukkit.World
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.world.WorldInitEvent
import org.bukkit.util.Vector
import java.io.File
import kotlin.concurrent.thread

class Arena(val name: String, var readableName: String,
            var worldborderSize: Int,
            var worldBorderCenter: Vector,
            var walls: ArrayList<DestructibleWall>,
            var chests: ArrayList<Vector>,
            var busStart: Vector,
            var busEnd: Vector): Listener {

    val arenaWorldName: String
        get() = "arena_$name"
    val arenaWorld: World
        get() = Bukkit.getWorld(arenaWorldName)

    init {
        this.register(fbrPlugin)
    }

    fun createBackup(callback: () -> Unit = {}) {
        if(Bukkit.getWorld(name) == null) throw IllegalStateException("World $name is not loaded!")
        val folder = Bukkit.getWorld(name).worldFolder
        val to = File(DataFolders.arenasFolder, name)
        if(to.exists()) {
            to.deleteRecursively()
        }
        thread {
            folder.copyTo(to)
            File(to, "uid.dat").delete()
            task { callback() }
        }
    }

    fun loadArenaWorldAsync() {
        if(Bukkit.getWorld(arenaWorldName) != null) Bukkit.unloadWorld(arenaWorldName, false)
        thread {
            val from = File(DataFolders.arenasFolder, name)
            val worldFolder = File(arenaWorldName)
            if(worldFolder.exists()) {
                worldFolder.deleteRecursively()
            }
            from.copyTo(worldFolder)
            task {
                generateEmptyWorld(arenaWorldName)
            }
        }

    }

    @EventHandler
    private fun onWorldLoad(e: WorldInitEvent) {
        if(e.world.name != arenaWorldName) return
        e.world.keepSpawnInMemory = false
    }


}