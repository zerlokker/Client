package dev.blend.command

import dev.blend.command.api.CommandInfo
import dev.blend.util.player.ChatUtil.format
import dev.blend.util.player.ChatUtil.info

abstract class Command {

    val commandInfo: CommandInfo
    val name get() = commandInfo.names.first()

    init {
        if (this::class.java.isAnnotationPresent(CommandInfo::class.java)) {
            commandInfo = this::class.java.getAnnotation(CommandInfo::class.java)
        } else {
            throw IllegalStateException("@CommandInfo not found on ${this::class.java.simpleName}")
        }
    }

    abstract fun execute(args: List<String>)

    protected fun usage() {
        info("Usage of ${format("@$name!")}:")
        commandInfo.syntax.forEach { syntax ->
            info(format(syntax), false)
        }
    }

}