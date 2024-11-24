package dev.blend

import dev.blend.command.api.CommandManager
import dev.blend.handler.api.HandlerManager
import dev.blend.module.api.ModuleManager
import org.slf4j.LoggerFactory

object Client {

    val name = "Blend"
    val logger = LoggerFactory.getLogger(name)

    fun initialize() {
        val initTime = System.currentTimeMillis()

        ModuleManager.initialize()
        CommandManager.initialize()
        HandlerManager.initialize()

        Runtime.getRuntime().addShutdownHook(Thread(this::shutdown, "Shutdown"))
        logger.info("Initialized $name in ${System.currentTimeMillis() - initTime}ms")
    }

    private fun shutdown() {

    }

}