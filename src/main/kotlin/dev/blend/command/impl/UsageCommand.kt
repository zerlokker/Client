package dev.blend.command.impl

import dev.blend.command.Command
import dev.blend.command.api.CommandInfo
import dev.blend.command.api.CommandManager
import dev.blend.util.player.ChatUtil

@CommandInfo(
    names = ["usage", "how", "?"],
    description = "Displays the proper usage of a command.",
    syntax = [".usage @{command}!"]
)
object UsageCommand: Command() {
    override fun execute(args: List<String>) {
        with(ChatUtil) {
            if (args.size > 1) {
                val commandName = args[1]
                CommandManager.commands
                    .forEach { command ->
                        command.commandInfo.names.forEach { name ->
                            if (name.equals(commandName, ignoreCase = true)) {
                                info("Usage of ${format("@${commandName}!")}:")
                                command.commandInfo.syntax.forEach { syntax ->
                                    info(format(syntax), false)
                                }
                            }
                        }
                    }
            } else {
                error(this@UsageCommand.commandInfo.syntax.first())
            }
        }
    }
}