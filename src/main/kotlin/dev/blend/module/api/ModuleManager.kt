package dev.blend.module.api

import dev.blend.module.Module
import dev.blend.util.interfaces.IManager

object ModuleManager: IManager {

    val modules = ArrayList<Module>()

    override fun initialize() {
        modules.addAll(arrayOf(

        ))
        modules.sortBy {
            it.getName()
        }
        modules.forEach {
            it.set(it.moduleInfo.enabled)
        }
        modules.forEach {
            it.key = it.moduleInfo.key
        }
    }

}