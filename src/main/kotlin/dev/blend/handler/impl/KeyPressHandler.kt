package dev.blend.handler.impl

import best.azura.eventbus.handler.EventHandler
import best.azura.eventbus.handler.Listener
import dev.blend.event.impl.KeyEvent
import dev.blend.handler.Handler
import dev.blend.module.api.ModuleManager
import org.lwjgl.glfw.GLFW

class KeyPressHandler: Handler {

    @EventHandler
    val keyEventListener: Listener<KeyEvent> = Listener { event ->
        if (event.action == GLFW.GLFW_PRESS) {
            ModuleManager.modules.filter {
                it.key == event.key
            }.forEach {
                it.toggle()
            }
        }
    }

}