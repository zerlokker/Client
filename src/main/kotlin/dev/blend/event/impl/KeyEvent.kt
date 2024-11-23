package dev.blend.event.impl

import best.azura.eventbus.core.Event

class KeyEvent(
    val window: Long,
    val key: Int,
    val scanCode: Int,
    val action: Int,
    val modifiers: Int
): Event