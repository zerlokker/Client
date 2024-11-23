package dev.blend.util.player

import dev.blend.Client
import dev.blend.util.IAccessor
import net.minecraft.text.Text
import net.minecraft.util.Formatting

object ChatUtil: IAccessor {

    @JvmStatic
    fun info(message: String, prefix: Boolean = true) {
        val text = Text.empty()
        if (prefix)
            text.append("${Formatting.AQUA}${Client.name} ")
        text.append("${Formatting.GREEN} » ")
        text.append("${Formatting.RESET}$message")
        mc.inGameHud.chatHud.addMessage(text)
    }
    @JvmStatic
    fun warn(message: String, prefix: Boolean = true) {
        val text = Text.empty()
        if (prefix)
            text.append("${Formatting.AQUA}${Client.name} ")
        text.append("${Formatting.GOLD} » ")
        text.append("${Formatting.RESET}$message")
        mc.inGameHud.chatHud.addMessage(text)
    }
    @JvmStatic
    fun error(message: String, prefix: Boolean = true) {
        val text = Text.empty()
        if (prefix)
            text.append("${Formatting.AQUA}${Client.name} ")
        text.append("${Formatting.RED} » ")
        text.append("${Formatting.RESET}$message")
        mc.inGameHud.chatHud.addMessage(text)
    }

}