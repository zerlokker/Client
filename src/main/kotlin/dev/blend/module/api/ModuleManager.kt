package dev.blend.module.api

import dev.blend.module.Module
import dev.blend.module.impl.client.ClickGUIModule
import dev.blend.util.interfaces.IManager

object ModuleManager: IManager {

    val modules = mutableListOf<Module>()

    override fun initialize() {
        modules.addAll(arrayOf(
            ClickGUIModule
        ))
        modules.sortBy {
            it.name
        }
        modules.forEach {
            it.set(it.moduleInfo.enabled)
        }
        modules.forEach {
            it.key = it.moduleInfo.key
        }
    }

}