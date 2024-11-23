package dev.blend.command.api

import dev.blend.command.Command
import dev.blend.util.interfaces.IManager

object CommandManager: IManager {

    val commands = mutableListOf<Command>()

    override fun initialize() {
        commands.addAll(arrayOf(

        ))
        commands.sortBy {
            it.getName()
        }
    }

}