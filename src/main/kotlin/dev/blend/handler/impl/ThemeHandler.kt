package dev.blend.handler.impl

import dev.blend.handler.Handler
import dev.blend.util.render.ColorUtil
import java.awt.Color

object ThemeHandler: Handler {

    val gray = Color(120, 120, 120)

    @JvmStatic
    fun getPrimary(): Color {
        return Color(0, 160, 255)
    }
    @JvmStatic
    fun getBackground(): Color {
        return Color(0, 16, 25)
    }
    @JvmStatic
    fun getTextColor(): Color {
        return Color(255, 255, 255)
    }
    @JvmStatic
    fun getContrast(): Color {
        return Color(255, 255, 255)
    }

}