package com.scarabcoder.fbr.util

import com.sk89q.worldedit.Vector
import org.bukkit.event.HandlerList
import org.bukkit.event.Listener

fun Listener.deregister() {
    HandlerList.unregisterAll(this)
}

fun Vector.bukkit(): org.bukkit.util.Vector {
    return org.bukkit.util.Vector(this.x, this.y, this.z)
}

fun org.bukkit.util.Vector.fawe(): Vector {
    return Vector(this.x, this.y, this.z)
}