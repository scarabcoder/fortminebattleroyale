package com.scarabcoder.fbr.configuration

import com.scarabcoder.commons.config.Config
import com.scarabcoder.commons.config.ConfigValue
import java.util.*

object GameConfiguration: Config("game") {

    init {
        saveOnUpdate = true
    }

    var enabled: Boolean by ConfigValue(false)
    var arenas: List<String> by ConfigValue(Collections.emptyList())
    var teams: Boolean by ConfigValue(true)
    var teamSize: Int by ConfigValue(4)
    var worldBorderSize: Int by ConfigValue(250)
    var maxPlayers: Int by ConfigValue(32)
    var minPlayers: Int by ConfigValue(12)
    var randomArenaSelection: Boolean by ConfigValue(false)
    var wallDamage: Float by ConfigValue(0.34f)
    var busSpeed: Double by ConfigValue(0.05)

    var lobbyTime: Long by ConfigValue(30)

    init {
        enabled
        arenas
        teams
        teamSize
        worldBorderSize
        maxPlayers
        minPlayers
        randomArenaSelection
        lobbyTime
        busSpeed
    }

}