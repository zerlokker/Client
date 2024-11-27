package dev.blend.ui.dropdown.components

import dev.blend.handler.impl.ThemeHandler
import dev.blend.module.api.Category
import dev.blend.module.api.ModuleManager
import dev.blend.ui.AbstractUIComponent
import dev.blend.util.render.Alignment
import dev.blend.util.render.DrawUtil

class CategoryComponent(
    private val category: Category
): AbstractUIComponent(
    width = 100.0,
    height = 20.0
) {

    val components = mutableListOf<ModuleComponent>()
    private val initialHeight = height;

    init {
        ModuleManager.modules.filter {
            it.moduleInfo.category == category
        }.forEach {
            components.add(ModuleComponent(this, it))
        }
    }

    override fun init() {
        components.forEach {
            it.init()
        }
    }

    override fun render(mouseX: Int, mouseY: Int) {
        DrawUtil.roundedRect(x, y, width, height, 5, ThemeHandler.getBackground())
        DrawUtil.drawString(category.properName, x + (width / 2), y + (initialHeight / 2), 12, ThemeHandler.getTextColor(), Alignment.CENTER)

        var veryRealHeight = initialHeight
        components.forEach {
            it.x = x
            it.y = y + veryRealHeight
            it.width = width
            it.render(mouseX, mouseY)
            veryRealHeight += it.height
        }
        this.height = veryRealHeight
    }

    override fun click(mouseX: Double, mouseY: Double, mouseButton: Int): Boolean {
        components.forEach {
            if (it.click(mouseX, mouseY, mouseButton)) {
                return true
            }
        }
        return false
    }

    override fun release(mouseX: Double, mouseY: Double, mouseButton: Int): Boolean {
        components.forEach {
            if (it.release(mouseX, mouseY, mouseButton)) {
                return true
            }
        }
        return false
    }

    override fun key(key: Int, scancode: Int, modifiers: Int): Boolean {
        components.forEach {
            if (it.key(key, scancode, modifiers)) {
                return true
            }
        }
        return false
    }

    override fun close() {
        components.forEach {
            it.close()
        }
    }

}