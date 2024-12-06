package dev.blend.ui.dropdown.components.values

import dev.blend.handler.impl.ThemeHandler
import dev.blend.ui.dropdown.components.AbstractValueComponent
import dev.blend.ui.dropdown.components.ModuleComponent
import dev.blend.util.render.Alignment
import dev.blend.util.render.DrawUtil
import dev.blend.value.impl.ColorValue

class ColorValueComponent(
    parent: ModuleComponent,
    override val value:  ColorValue
): AbstractValueComponent(
    parent, value, height = 20.0
) {
    override fun init() {

    }

    override fun render(mouseX: Int, mouseY: Int) {
        val offset = 5.0
        with(DrawUtil) {
            drawString(value.name, x + 5.0, y + (height / 2.0), 8, ThemeHandler.getTextColor(), Alignment.CENTER_LEFT)
            roundedRect(x + (width - offset), y + (height / 2.0), 20.0, height - (offset * 2.0), 2.0, value.get(), Alignment.CENTER_RIGHT)
            roundedRect(x + (width - offset), y + (height / 2.0), 20.0, height - (offset * 2.0), 2.0, 1.0, ThemeHandler.getContrast(), Alignment.CENTER_RIGHT)
        }
    }

    override fun click(mouseX: Double, mouseY: Double, mouseButton: Int): Boolean {
        return false
    }

    override fun release(mouseX: Double, mouseY: Double, mouseButton: Int): Boolean {
        return false
    }

    override fun key(key: Int, scancode: Int, modifiers: Int): Boolean {
        return false
    }

    override fun close() {

    }
}