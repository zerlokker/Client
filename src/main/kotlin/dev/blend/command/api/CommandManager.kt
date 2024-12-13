package dev.blend.command.api

import dev.blend.command.Command
import dev.blend.command.impl.ConfigCommand
import dev.blend.command.impl.HelpCommand
import dev.blend.command.impl.ListCommand
import dev.blend.command.impl.UsageCommand
import dev.blend.util.interfaces.IManager

object CommandManager: IManager {

    val commands = mutableListOf<Command>()

    override fun initialize() {
        commands.addAll(arrayOf(
            HelpCommand(),
            ListCommand(),
            ConfigCommand(),
            UsageCommand,
        ))
        commands.sortBy {
            it.name
        }
    }

}