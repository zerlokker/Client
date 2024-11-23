package dev.blend.module.api

import org.lwjgl.glfw.GLFW

annotation class ModuleInfo(
    val names: Array<String>,
    val description: String,
    val category: Category,
    val enabled: Boolean = false,
    val key: Int = GLFW.GLFW_KEY_UNKNOWN
)
