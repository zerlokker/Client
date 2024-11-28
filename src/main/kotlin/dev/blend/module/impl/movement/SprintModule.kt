package dev.blend.module.impl.movement

import best.azura.eventbus.handler.EventHandler
import best.azura.eventbus.handler.Listener
import dev.blend.event.impl.PreMotionEvent
import dev.blend.interfaces.KeybindingAccessor
import dev.blend.module.Module
import dev.blend.module.api.Category
import dev.blend.module.api.ModuleInfo
import net.minecraft.client.util.InputUtil

@ModuleInfo(
    names = ["Sprint"],
    description = "Makes the player sprint.",
    category = Category.MOVEMENT,
    enabled = true
)
object SprintModule: Module() {

    override fun onDisable() {
        mc.options.sprintKey.isPressed = InputUtil.isKeyPressed(
            mc.window.handle,
            (mc.options.sprintKey as KeybindingAccessor).boundKey.code
        )
    }

    @EventHandler
    val preMotion: Listener<PreMotionEvent> = Listener {
        mc.options.sprintKey.isPressed = true
    }

}