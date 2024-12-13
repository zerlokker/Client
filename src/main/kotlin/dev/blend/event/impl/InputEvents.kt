package dev.blend.event.impl

import best.azura.eventbus.core.Event
import best.azura.eventbus.events.CancellableEvent

class KeyEvent(
    val window: Long,
    val key: Int,
    val scanCode: Int,
    val action: Int,
    val modifiers: Int
): Event

class ChatSendEvent(
    val message: String
): CancellableEvent()