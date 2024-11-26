package dev.blend.ui.dropdown.components

import dev.blend.handler.impl.ThemeHandler
import dev.blend.module.api.Category
import dev.blend.ui.AbstractUIComponent
import dev.blend.util.render.DrawUtil

class CategoryComponent(
    val category: Category
): AbstractUIComponent(
    width = 100.0,
    height = 20.0
) {

    override fun init() {

    }

    override fun render(mouseX: Int, mouseY: Int) {
        DrawUtil.roundedRect(x, y, width, height, 5, ThemeHandler.getPrimary())
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