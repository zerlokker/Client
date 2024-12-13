package dev.blend.command.impl

import dev.blend.command.Command
import dev.blend.command.api.CommandInfo
import dev.blend.command.api.CommandManager
import dev.blend.util.player.ChatUtil

@CommandInfo(
    names = ["list", "l"],
    description = "Lists all available commands.",
    syntax = [".list"]
)
class ListCommand: Command() {
    override fun execute(args: List<String>) {
        with(ChatUtil) {
            info("List of all available commands: ")
            CommandManager.commands.forEach { command ->
                info("${aquafy(command.name)}: ${command.commandInfo.description}", false)
            }
        }
    }
}