package dev.blend

import org.slf4j.LoggerFactory

object Client {

    val client = "Blend"
    val logger = LoggerFactory.getLogger(client)

    fun initialize() {
        val initTime = System.currentTimeMillis()
        Runtime.getRuntime().addShutdownHook(Thread(this::shutdown, "Shutdown"))
        logger.info("Initialized $client in ${System.currentTimeMillis() - initTime}ms")
    }

    private fun shutdown() {

    }

}