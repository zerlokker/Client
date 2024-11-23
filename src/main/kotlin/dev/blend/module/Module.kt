package dev.blend.module

import dev.blend.module.api.ModuleInfo
import dev.blend.util.IAccessor
import org.lwjgl.glfw.GLFW

abstract class Module: IAccessor {

    val moduleInfo: ModuleInfo
    var key = GLFW.GLFW_KEY_UNKNOWN
    var enabled = false

    init {
        if (this::class.java.isAnnotationPresent(ModuleInfo::class.java)) {
            moduleInfo = this::class.java.getAnnotation(ModuleInfo::class.java)
        } else {
            throw IllegalStateException("@ModuleInfo not found on ${this.javaClass.simpleName}")
        }
    }

    fun onEnable() {}
    fun onDisable() {}

    fun toggle() {
        set(!enabled)
    }
    fun set(enabled: Boolean) {
        if (this.enabled != enabled) {
            this.enabled = enabled
            if (this.enabled) {
                onEnable()
            } else {
                onDisable()
            }
        }
    }

    fun getName(): String {
        return moduleInfo.names[0]
    }

}