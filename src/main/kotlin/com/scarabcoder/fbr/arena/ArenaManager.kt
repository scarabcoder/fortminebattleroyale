package com.scarabcoder.fbr.arena

import com.scarabcoder.fbr.configuration.DataFolders
import com.scarabcoder.fbr.fbrPlugin
import org.bukkit.Bukkit
import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.util.Vector
import java.io.File
import java.util.*

object ArenaManager {

    private val _arenas = ArrayList<Arena>()
    val arenas: List<Arena>
        get() = _arenas.toList()

    fun loadArenas() {

        DataFolders.arenasFolder.listFiles().filter { it.extension == "yml" }.forEach {
            val cfg = YamlConfiguration.loadConfiguration(it)

            val arena = Arena(
                    name = cfg.getString("name"),
                    readableName = cfg.getString("readable-name"),
                    worldBorderCenter = cfg.getVector("worldborder-center"),
                    worldborderSize = cfg.getInt("worldborder-size"),
                    chests = cfg.get("chests") as ArrayList<Vector>,
                    walls = cfg.get("walls") as ArrayList<DestructibleWall>
            )

            _arenas.add(arena)
            fbrPlugin.logger.info("Loaded arena ${arena.name}")
        }

    }

    fun getArena(name: String): Arena? {
        return _arenas.firstOrNull { it.name == name }
    }

    fun removeArena(arena: Arena) {
        _arenas.remove(arena)

        File(DataFolders.arenasFolder, arena.name + ".yml").delete()
        File(DataFolders.arenasFolder, arena.name).deleteRecursively()

    }

    fun createArena(name: String): Arena {
        if(_arenas.any { it.name == name }) throw IllegalArgumentException("Arena with name $name already exists!")
        if(Bukkit.getWorld(name) == null) throw IllegalArgumentException("World must exist with name $name!")

        val arenaCfgFile = File(DataFolders.arenasFolder, "$name.yml")
        arenaCfgFile.createNewFile()

        val arena = Arena(name, name, 50, Vector(0,0,0), ArrayList(), ArrayList())
        saveArena(arena)
        _arenas.add(arena)
        return arena
    }

    fun saveArena(arena: Arena) {
        val cfgFile = File(DataFolders.arenasFolder, "${arena.name}.yml")
        val cfg = YamlConfiguration.loadConfiguration(cfgFile)
        cfg.set("name", arena.name)
        cfg.set("readable-name", arena.readableName)
        cfg.set("worldborder-center", arena.worldBorderCenter)
        cfg.set("worldborder-size", arena.worldborderSize)
        cfg.set("chests", arena.chests)
        cfg.set("walls", arena.walls)
        cfg.save(cfgFile)
    }



}