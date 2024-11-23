package dev.blend

import net.fabricmc.api.ModInitializer

class Initializer : ModInitializer {

    override fun onInitialize() {
        Client.initialize()
    }

}
