package dev.blend.command

import dev.blend.command.api.CommandInfo

abstract class Command {

    val commandInfo: CommandInfo

    init {
        if (this::class.java.isAnnotationPresent(CommandInfo::class.java)) {
            commandInfo = this::class.java.getAnnotation(CommandInfo::class.java)
        } else {
            throw IllegalStateException("@CommandInfo not found on ${this::class.java.simpleName}")
        }
    }

    fun getName(): String {
        return commandInfo.names[0]
    }

}