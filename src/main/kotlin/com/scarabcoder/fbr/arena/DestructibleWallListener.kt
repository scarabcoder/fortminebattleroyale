package com.scarabcoder.fbr.arena

import com.scarabcoder.commons.doTimer
import com.scarabcoder.commons.iterate
import com.scarabcoder.commons.register
import com.scarabcoder.fbr.BattleRoyale
import com.scarabcoder.fbr.configuration.GameConfiguration
import com.scarabcoder.fbr.fbrPlugin
import com.scarabcoder.fbr.util.sendPacket
import net.minecraft.server.v1_12_R1.BlockPosition
import net.minecraft.server.v1_12_R1.PacketPlayOutBlockBreakAnimation
import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent
import java.util.concurrent.ThreadLocalRandom
import kotlin.math.roundToInt

class DestructibleWallListener(private val arena: Arena): Listener {

    private val destroyedProgressUpdater: () -> Unit

    val wallDamage = HashMap<DestructibleWall, Float>()

    init {
        arena.walls.forEach { wallDamage.put(it, 0.0f) }

        destroyedProgressUpdater = {

            for((wall, damage) in wallDamage) {

                if(damage == 0.0f || damage >= 1.0f) continue

                for(vec in wall.first.iterate(wall.second)) {
                    val packet = PacketPlayOutBlockBreakAnimation(ThreadLocalRandom.current().nextInt(0, Integer.MAX_VALUE), BlockPosition(vec.x, vec.y, vec.z), (9 * damage).roundToInt())
                    BattleRoyale.players.forEach { it.sendPacket(packet) }
                }

            }

        }

        doTimer(80) {
            destroyedProgressUpdater()
        }
        register(fbrPlugin)
    }

    @EventHandler
    fun onPlayerBreakWall(e: PlayerInteractEvent) {
        if(e.action != Action.LEFT_CLICK_BLOCK) return
        //TODO: Check for correct wall-breaking item

        for(wall in arena.walls) {

            val l = e.clickedBlock.location
            val f = wall.first
            val s = wall.second

            if(!(l.blockX in f.blockX..s.blockX &&
                    l.blockY in f.blockY..s.blockY &&
                    l.blockZ in f.blockZ..s.blockZ)) {
                continue
            }

            //TODO: Play extra destruction effects/sounds

            wallDamage.put(wall, wallDamage[wall]!! + GameConfiguration.wallDamage)

            destroyedProgressUpdater()

            if(wallDamage[wall]!! >= 1.0f) {

                for(vec in f.iterate(s)) {
                    vec.toLocation(arena.arenaWorld).block.type = Material.AIR
                }

            }
        }
    }

}