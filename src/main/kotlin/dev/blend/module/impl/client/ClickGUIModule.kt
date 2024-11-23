package dev.blend.module.impl.client

import dev.blend.module.Module
import dev.blend.module.api.Category
import dev.blend.module.api.ModuleInfo
import org.lwjgl.glfw.GLFW

@ModuleInfo(
    names = ["ClickGUI", "GUI"],
    description = "Displays a GUI for the user to edit the client's features in.",
    category = Category.CLIENT,
    key = GLFW.GLFW_KEY_RIGHT_SHIFT
)
object ClickGUIModule: Module() {

    override fun onEnable() {

    }

    override fun onDisable() {

    }

}