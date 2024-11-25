package dev.blend.module.impl.client

import dev.blend.module.Module
import dev.blend.module.api.Category
import dev.blend.module.api.ModuleInfo

@ModuleInfo(
    names = ["Theme", "Colors"],
    description = "Customize the client's look and feel",
    category = Category.CLIENT
)
object ThemeModule: Module() {

    override fun onEnable() {
        this.set(false)
    }

}