package dev.blend.ui.dropdown.components.values

import dev.blend.handler.impl.ThemeHandler
import dev.blend.ui.dropdown.components.AbstractValueComponent
import dev.blend.ui.dropdown.components.ModuleComponent
import dev.blend.util.render.Alignment
import dev.blend.util.render.DrawUtil
import dev.blend.value.impl.ModeValue

class ModeValueComponent(
    parent: ModuleComponent,
    override val value:  ModeValue
): AbstractValueComponent(
    parent, value, height = 20.0
) {

    override fun init() {

    }

    override fun render(mouseX: Int, mouseY: Int) {
        with(DrawUtil) {
            drawString(value.name, x + padding, y + (height / 2.0), 8, ThemeHandler.getTextColor(), Alignment.CENTER_LEFT)
            drawString(value.get(), (x + width) - padding, y + (height / 2.0), 8, ThemeHandler.getTextColor(), Alignment.CENTER_RIGHT)
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