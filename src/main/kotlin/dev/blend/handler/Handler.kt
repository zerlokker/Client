package dev.blend.handler

import dev.blend.event.api.EventBus

interface Handler {
    fun register() {
        EventBus.subscribe(this)
    }
}