package dev.blend.event.impl

import best.azura.eventbus.core.Event

class PreMotionEvent(
    var x: Double,
    var y: Double,
    var z: Double,
    var yaw: Float,
    var pitch: Float,
    var onGround: Boolean,
): Event