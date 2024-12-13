package dev.blend.command.impl

import dev.blend.command.Command
import dev.blend.command.api.CommandInfo
import dev.blend.util.player.ChatUtil

@CommandInfo(
    names = ["help", "wtf", "h"],
    description = "Helps users with chat commands.",
    syntax = [".help", ".help @{command}"]
)
class HelpCommand: Command() {
    override fun execute(args: List<String>) {
        with(ChatUtil) {
            if (args.size > 1) {
                UsageCommand.execute(args)
            } else {
                info("Help: ")
                info("${format("@.list!")} for a list of all available commands.", false)
                info("${format("@.usage!")} for proper usage of a command.", false)
            }
        }
    }
}