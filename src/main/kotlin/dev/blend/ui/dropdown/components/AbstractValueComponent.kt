package dev.blend.ui.dropdown.components

import dev.blend.ui.AbstractUIComponent
import dev.blend.value.Value

abstract class AbstractValueComponent(
    val parent: ModuleComponent,
    open val value: Value<*>,
    width: Double = 0.0,
    height: Double = 0.0,
): AbstractUIComponent(
    width = width,
    height = height
) {
    val padding = 5.0
    open fun isExpanding(): Boolean {
        return false
    }
}