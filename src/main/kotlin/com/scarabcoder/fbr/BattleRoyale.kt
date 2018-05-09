package com.scarabcoder.fbr

import com.scarabcoder.commons.doTimer
import com.scarabcoder.fbr.arena.Arena
import com.scarabcoder.fbr.arena.DestructibleWallListener
import com.scarabcoder.fbr.chat.PlayerChatSettings
import com.scarabcoder.fbr.configuration.GameConfiguration
import com.scarabcoder.fbr.game.GamePeriod
import com.scarabcoder.fbr.game.GameTimer
import com.scarabcoder.fbr.game.TeamManager
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.scheduler.BukkitTask
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.collections.ArrayList

object BattleRoyale {

    var period: GamePeriod = GamePeriod.WAITING
    var timer: GameTimer? = null
    lateinit var teamManager: TeamManager
    lateinit var playerChatSetting: PlayerChatSettings
    lateinit var destructibleWallListener: DestructibleWallListener
    val players: List<Player>
        get() = Bukkit.getOnlinePlayers().toList()
    val ingamePlayers: List<Player>
        get() = Bukkit.getOnlinePlayers().filter { !_spectators.contains(it.uniqueId) }
    val spectators: List<Player>
        get() = Bukkit.getOnlinePlayers().filter { _spectators.contains(it.uniqueId) }
    private var _spectators = ArrayList<UUID>()
    lateinit var tickingTimer: BukkitTask
    var lastSecond: Long = 0

    fun startGame(arena: Arena) {
        if(this::tickingTimer.isInitialized) {
            tickingTimer.cancel()
        }
        _spectators = ArrayList()
        period = GamePeriod.WAITING
        teamManager = TeamManager()
        playerChatSetting = PlayerChatSettings()
        destructibleWallListener = DestructibleWallListener(arena)

        arena.loadArenaWorldAsync()
        tickingTimer = doTimer(1) { tick() }
    }

    fun setSpectating(player: Player, spectating: Boolean) {
        if(spectating) _spectators.add(player.uniqueId)
        else _spectators.remove(player.uniqueId)
    }

    private fun tick() {

        if(ingamePlayers.size >= GameConfiguration.minPlayers) {

            //TODO: Send game starting soon broadcast
            period = GamePeriod.STARTING
            timer = GameTimer.startTimer(GameConfiguration.lobbyTime, TimeUnit.SECONDS) {
                lobbyEnd()
            }
        }

        if(System.currentTimeMillis() - lastSecond >= 1000) {
            second()
            lastSecond = System.currentTimeMillis()
        }


    }

    private fun second() {

    }

    private fun lobbyEnd() {

    }


}