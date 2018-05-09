package com.scarabcoder.fbr.chat

import org.bukkit.entity.Player
import java.util.*

class PlayerChatSettings {

    private val chatChannels = HashMap<UUID, ChatChannel>()

    fun getChannel(player: Player): ChatChannel {
        return chatChannels.getOrDefault(player.uniqueId, ChatChannel.GLOBAL)
    }

    fun setChannel(player: Player, channel: ChatChannel) {
        chatChannels.put(player.uniqueId, channel)
    }

}