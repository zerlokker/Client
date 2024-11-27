package dev.blend.ui.dropdown.components.values

import dev.blend.ui.dropdown.components.AbstractValueComponent
import dev.blend.ui.dropdown.components.ModuleComponent
import dev.blend.value.impl.BooleanValue

class BooleanValueComponent(
    parent: ModuleComponent,
    value: BooleanValue
): AbstractValueComponent(
    parent, value
) {
    override fun init() {

    }

    override fun render(mouseX: Int, mouseY: Int) {

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