package dev.blend.ui.dropdown.components.values

import dev.blend.Client
import dev.blend.handler.impl.ThemeHandler
import dev.blend.ui.dropdown.components.AbstractValueComponent
import dev.blend.ui.dropdown.components.ModuleComponent
import dev.blend.util.animations.SineOutAnimation
import dev.blend.util.render.Alignment
import dev.blend.util.render.ColorUtil
import dev.blend.util.render.DrawUtil
import dev.blend.util.render.DrawUtil.drawString
import dev.blend.value.impl.ModeValue
import org.lwjgl.glfw.GLFW

class ModeValueComponent(
    parent: ModuleComponent,
    override val value:  ModeValue
): AbstractValueComponent(
    parent, value, height = 20.0
) {

    val expandAnimation = SineOutAnimation()
    val expandToggleAnimation = SineOutAnimation()
    private val initialHeight = height
    private var expanded = false

    override fun init() {

    }

    override fun render(mouseX: Int, mouseY: Int) {
        var veryTemporaryHeight = initialHeight
        var yPos = y + (initialHeight / 2.0)
        val hi = ColorUtil.mixColors(ThemeHandler.getBackground(), ThemeHandler.getPrimary(), 0.1)
        with(DrawUtil) {
            val maxStringWidth = value.modes.maxOf { getStringWidth("regular", it, 8) }
            save()
            intersectScissor(x, y, width, height)
            roundedRect((x + width) - (padding / 2.0), y + (padding / 2.0), maxStringWidth + padding, height - padding, 2, hi, Alignment.TOP_RIGHT)
            drawString(value.name, x + padding, yPos, 8, ThemeHandler.getTextColor(), Alignment.CENTER_LEFT)
            drawString(value.get(), (x + width) - padding, yPos, 8, ThemeHandler.getTextColor(), Alignment.CENTER_RIGHT)
            yPos += 14.0
            value.modes.filter {
                !it.equals(value.get(), true)
            }.forEach{ mode ->
                drawString(mode, (x + width) - padding, yPos, 8, ThemeHandler.getTextColor(), Alignment.CENTER_RIGHT)
                yPos += 14.0
                veryTemporaryHeight += 14.0
            }
            restore()
        }
        this.height = expandAnimation.get()
        expandAnimation.animate(if (expanded) veryTemporaryHeight else initialHeight)
        expandToggleAnimation.animate(if (expanded) 1.0 else 0.0)
    }

    override fun click(mouseX: Double, mouseY: Double, mouseButton: Int): Boolean {
        var yPos = y + (initialHeight / 2.0)
        if (isOver(x, y, width, initialHeight, mouseX, mouseY)) {
            expanded = !expanded
            return true
        }
        if (expanded) {
            yPos += 14.0
            value.modes.filter {
                !it.equals(value.get(), true)
            }.forEach{ mode ->
                if (isOver(x, yPos - (initialHeight / 2.0), width, 14.0, mouseX, mouseY)) {
                    value.set(mode)
                    expanded = false
                    return true
                }
                yPos += 14.0
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

    }

    override fun isExpanding(): Boolean {
        return !expandAnimation.finished
    }

}