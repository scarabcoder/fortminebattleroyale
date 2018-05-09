package com.scarabcoder.fbr.arena

import org.bukkit.Location
import org.bukkit.configuration.serialization.ConfigurationSerializable
import org.bukkit.util.Vector

class DestructibleWall(var first: Vector, var second: Vector): ConfigurationSerializable {

    override fun serialize(): MutableMap<String, Any> {
        val map = HashMap<String, Any>()
        map.put("first", first)
        map.put("second", second)
        return map
    }


    companion object {
        @JvmStatic
        fun deserialize(args: Map<String, Any>): DestructibleWall {
            return DestructibleWall(args["first"] as Vector, args["second"] as Vector)
        }

    }

}