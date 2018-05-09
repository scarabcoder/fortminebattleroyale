package com.scarabcoder.fbr.util

import net.minecraft.server.v1_12_R1.Packet
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer
import org.bukkit.entity.Player

fun Player.sendPacket(packet: Packet<*>): Unit = (player as CraftPlayer).handle.playerConnection.sendPacket(packet)