package dev.blend.module.impl.client

import dev.blend.module.Module
import dev.blend.module.api.Category
import dev.blend.module.api.ModuleInfo
import dev.blend.value.impl.ColorValue
import dev.blend.value.impl.ModeValue
import dev.blend.value.impl.NumberValue
import java.awt.Color

@ModuleInfo(
    names = ["Theme", "Colors"],
    description = "Customize the client's look and feel",
    category = Category.CLIENT
)
object ThemeModule: Module() {

    val accent = ColorValue("Accent", this, Color(0, 160, 255))
    val secondary = ColorValue("Secondary", this, Color(160, 0, 255))
    val theme = ModeValue("Theme", this, arrayOf("Dark", "Light"))
    val font = ModeValue("Font", this, arrayOf("Regular", "Ubuntu"))
    val gradientSpeed = NumberValue("Speed", this, 500.0, 200.0, 2000.0, 100.0)

    override fun onEnable() {
        this.set(false)
    }

}