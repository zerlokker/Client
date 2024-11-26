package dev.blend.ui

import dev.blend.util.interfaces.IScreen

abstract class AbstractUIComponent(
    var x: Double = 0.0,
    var y: Double = 0.0,
    var width: Double = 0.0,
    var height: Double = 0.0,
): IScreen {
    fun isOver(mouseX: Number, mouseY: Number): Boolean {
        return x > mouseX.toDouble() && y > mouseY.toDouble() && x + width < mouseX.toDouble() && y + height < mouseY.toDouble()
    }
    fun isOver(x: Number, y: Number, width: Number, height: Number, mouseX: Number, mouseY: Number): Boolean {
        return x.toDouble() > mouseX.toDouble() && y.toDouble() > mouseY.toDouble() && x.toDouble() + width.toDouble() < mouseX.toDouble() && y.toDouble() + height.toDouble() < mouseY.toDouble()
    }
}