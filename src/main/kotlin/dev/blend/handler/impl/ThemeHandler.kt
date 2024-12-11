package dev.blend.handler.impl

import dev.blend.handler.Handler
import dev.blend.module.impl.client.ThemeModule
import dev.blend.util.animations.SineOutAnimation
import dev.blend.util.render.ColorUtil
import java.awt.Color

object ThemeHandler: Handler {

    private val hi = SineOutAnimation()
    val gray = Color(120, 120, 120)
    val colormode = ThemeModule.theme.get()

    @JvmStatic
    fun getPrimary(): Color {
        return ThemeModule.accent.get()
    }
    @JvmStatic
    fun getBackground(): Color {
        return ColorUtil.mixColors(getBaseColor(), getPrimary(), 0.05)
    }
    @JvmStatic
    fun getTextColor(): Color {
        return Color(255, 255, 255)
    }
    @JvmStatic
    fun getContrast(): Color {
        return Color(255, 255, 255)
    }
    fun getBaseColor(): Color {
        return Color(hi.get().toInt(), hi.get().toInt(), hi.get().toInt())
    }

}