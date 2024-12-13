package dev.blend.handler.impl

import best.azura.eventbus.handler.EventHandler
import best.azura.eventbus.handler.Listener
import dev.blend.command.api.CommandManager
import dev.blend.event.impl.ChatSendEvent
import dev.blend.handler.Handler
import dev.blend.util.player.ChatUtil

class ChatMessageHandler: Handler {

    var errorCounter = 0

    @EventHandler
    val chatEventListener = Listener<ChatSendEvent> { event ->
        val message = event.message
        if (message.isNotEmpty() && message.startsWith(".") && message.length > 1 && !message.startsWith("..")) {
            event.isCancelled = true
            mc.inGameHud.chatHud.addToMessageHistory(message);
            val messages = message
                .substring(1)
                .split(" ")
            CommandManager.commands.forEach { command ->
                command.commandInfo.names.forEach { commandAlias ->
                    if (messages.first().equals(commandAlias, true)) {
                        command.execute(messages)
                        return@Listener
                    }
                }
            }
            errorCounter++
            with(ChatUtil) {
                error("Failed to find command ${aquafy(messages.first())}.")
                if (errorCounter > 1) {
                    info("Try ${aquafy(".help")}.", false)
                    errorCounter = 0
                }
            }
        }
    }

}