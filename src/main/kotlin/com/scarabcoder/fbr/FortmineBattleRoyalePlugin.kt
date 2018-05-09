package com.scarabcoder.fbr

import com.scarabcoder.fbr.arena.ArenaManager
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin

class FortmineBattleRoyalePlugin : JavaPlugin() {

    override fun onEnable() {
        ArenaManager.loadArenas()
    }

    override fun onDisable() {
        ArenaManager.arenas.forEach(ArenaManager::saveArena)
    }

}

val fbrPlugin by lazy {
    Bukkit.getPluginManager().getPlugin("FortmineBattleRoyale") as JavaPlugin
}