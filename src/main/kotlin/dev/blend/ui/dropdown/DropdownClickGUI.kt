package dev.blend.ui.dropdown

import dev.blend.util.render.DrawUtil
import net.minecraft.client.gui.DrawContext
import net.minecraft.client.gui.screen.Screen
import net.minecraft.text.Text
import java.awt.Color

object DropdownClickGUI: Screen(Text.of("Dropdown Click GUI")) {

    override fun render(context: DrawContext?, mouseX: Int, mouseY: Int, delta: Float) {
        DrawUtil.begin()
        DrawUtil.rect(100, 100, 100, 100, Color.white)
        DrawUtil.end()
    }

}