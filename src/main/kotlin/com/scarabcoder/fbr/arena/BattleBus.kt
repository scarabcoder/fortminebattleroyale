package com.scarabcoder.fbr.arena

import com.scarabcoder.commons.iterate
import com.scarabcoder.fbr.util.bukkit
import com.scarabcoder.fbr.util.fawe
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormat
import net.minecraft.server.v1_12_R1.Blocks
import net.minecraft.server.v1_12_R1.EntityFallingBlock
import net.minecraft.server.v1_12_R1.IBlockData
import net.minecraft.server.v1_12_R1.WorldServer
import org.bukkit.craftbukkit.v1_12_R1.CraftWorld
import java.io.File

class BattleBus(arena: Arena) {

    val blocks = ArrayList<EntityFallingBlock>()


    init {

        val clip = ClipboardFormat.SCHEMATIC.load(null as File?).clipboard!!

        for(vec in clip.minimumPoint.bukkit().iterate(clip.maximumPoint.bukkit())) {
            val block = clip.getBlock(vec.fawe())
            val w = (arena.arenaWorld as CraftWorld).handle

            val bd = Blocks.SANDSTONE.blockData as IBlockData
            

            val fallingBlock = EntityFallingBlock(w.getData)
        }


    }

}