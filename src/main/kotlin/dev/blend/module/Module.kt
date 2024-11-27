package dev.blend.module

import dev.blend.event.api.EventBus
import dev.blend.module.api.ModuleInfo
import dev.blend.util.IAccessor
import dev.blend.value.api.ValueHolder
import org.lwjgl.glfw.GLFW

abstract class Module: IAccessor, ValueHolder() {

    val moduleInfo: ModuleInfo
    var key = GLFW.GLFW_KEY_UNKNOWN
    val name: String
    private var enabled = false

    init {
        if (this::class.java.isAnnotationPresent(ModuleInfo::class.java)) {
            moduleInfo = this::class.java.getAnnotation(ModuleInfo::class.java)
        } else {
            throw IllegalStateException("@ModuleInfo not found on ${this::class.java.simpleName}")
        }
        name = moduleInfo.names.first()
    }

    open fun onEnable() {}
    open fun onDisable() {}

    fun toggle() {
        set(!enabled)
    }
    fun set(enabled: Boolean) {
        if (this.enabled != enabled) {
            this.enabled = enabled
            if (this.enabled) {
                onEnable()
                EventBus.subscribe(this)
            } else {
                EventBus.unsubscribe(this)
                onDisable()
            }
        }
    }
    fun get(): Boolean {
        return enabled
    }

}