package dev.blend.handler

import dev.blend.event.api.EventBus
import dev.blend.util.IAccessor

interface Handler: IAccessor {
    fun register() {
        EventBus.subscribe(this)
    }
}