package dev.blend.command.impl

import dev.blend.Client
import dev.blend.command.Command
import dev.blend.command.api.CommandInfo
import dev.blend.config.ConfigManager
import dev.blend.util.player.ChatUtil
import java.awt.Desktop

@CommandInfo(
    names = ["config", "profile", "configs", "profiles", "c", "f"],
    description = "Save the current configuration to a file.",
    syntax = [".config @save! _name", ".config @load! _name", ".config @folder!", ".config @list!"],
)
class ConfigCommand: Command() {
    override fun execute(args: List<String>) {
        with(ChatUtil) {
            if (args.size > 1) {
                val argument = args[1].lowercase()
                // save, load
                if (args.size > 2) {
                    val file = args[2].lowercase().replace(".json", "")
                    when (argument) {
                        "load" -> {
                            if (ConfigManager.load(file)) {
                                info("Loaded config ${format("@$file!")} successfully.")
                            } else {
                                error("Failed to load config ${format("%$file!")}.")
                            }
                        }
                        "save" -> {
                            if (ConfigManager.save(file)) {
                                info("Saved config ${format("@$file!")} successfully.")
                            } else {
                                warn("Failed to save config ${format("%$file!")}.")
                            }
                        }
                        else -> {
                            usage()
                        }
                    }
                } else {
                    when (argument) {
                        "folder" -> {
                            try {
                                Desktop.getDesktop().open(ConfigManager.configDir)
                                info("Opened config folder.")
                            } catch (hi: Exception) {
                                error("Error opening config directory ${format("%:(")}")
                            }
                        }
                        "list" -> {
                            ConfigManager.listConfigs()
                        }
                        else -> usage()
                    }
                }

            } else {
                usage()
            }
        }
    }
}