package dev.blend.ui.dropdown.components

import dev.blend.handler.impl.ThemeHandler
import dev.blend.module.Module
import dev.blend.ui.AbstractUIComponent
import dev.blend.ui.dropdown.components.values.BooleanValueComponent
import dev.blend.ui.dropdown.components.values.ColorValueComponent
import dev.blend.ui.dropdown.components.values.ModeValueComponent
import dev.blend.ui.dropdown.components.values.NumberValueComponent
import dev.blend.util.render.Alignment
import dev.blend.util.render.ColorUtil
import dev.blend.util.render.DrawUtil
import dev.blend.value.impl.BooleanValue
import dev.blend.value.impl.ColorValue
import dev.blend.value.impl.ModeValue
import dev.blend.value.impl.NumberValue
import org.lwjgl.glfw.GLFW

class ModuleComponent(
    private val parent: CategoryComponent,
    private val module: Module
): AbstractUIComponent(
    width = parent.width,
    height = parent.height
) {

    val components = mutableListOf<AbstractValueComponent>()
    private val initialHeight = height
    private var expanded = false
    private var last = false

    init {
        module.values.forEach {
            when (it) {
                is BooleanValue -> components.add(BooleanValueComponent(this, it))
                is NumberValue -> components.add(NumberValueComponent(this, it))
                is ColorValue -> components.add(ColorValueComponent(this, it))
                is ModeValue -> components.add(ModeValueComponent(this, it))
                else -> throw IllegalArgumentException("There is no component defined for ${it::class.simpleName} in Dropdown ClickGUI")
            }
        }
    }

    override fun init() {
        components.forEach {
            it.init()
        }
    }

    override fun render(mouseX: Int, mouseY: Int) {
        DrawUtil.save()
        DrawUtil.scissor(x, y, width, height)
        if (module.get()) {
            if (last) {
                DrawUtil.roundedRect(x, y, width, initialHeight, doubleArrayOf(0.0, 0.0, 5.0, 5.0), ColorUtil.applyOpacity(ThemeHandler.getPrimary(), 0.75))
            } else {
                DrawUtil.rect(x, y, width, initialHeight, ColorUtil.applyOpacity(ThemeHandler.getPrimary(), 0.75))
            }
        }
        DrawUtil.drawString(module.name, x + (width / 2), y + (initialHeight / 2), 12, ThemeHandler.getTextColor(), Alignment.CENTER)

        var veryRealHeight = initialHeight
        components.forEach {
            it.x = x
            it.y = y + veryRealHeight
            it.width = width
            it.render(mouseX, mouseY)
            veryRealHeight += it.height
        }
        DrawUtil.resetScissor()
        DrawUtil.restore()

        height = if (expanded) veryRealHeight else initialHeight
        last = parent.components.last() == this && !expanded
    }

    override fun click(mouseX: Double, mouseY: Double, mouseButton: Int): Boolean {
        if (isOver(x, y, width, initialHeight, mouseX, mouseY)) {
            if (mouseButton == GLFW.GLFW_MOUSE_BUTTON_LEFT) {
                module.toggle()
            } else {
                expanded = !expanded
            }
        }
        return false
    }

    override fun release(mouseX: Double, mouseY: Double, mouseButton: Int): Boolean {
        return false
    }

    override fun key(key: Int, scancode: Int, modifiers: Int): Boolean {
        return false
    }

    override fun close() {
        components.forEach {
            it.close()
        }
    }

}