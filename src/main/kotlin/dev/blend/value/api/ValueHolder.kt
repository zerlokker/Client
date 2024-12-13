package dev.blend.value.api

import dev.blend.value.Value

abstract class ValueHolder {
    val values = mutableListOf<Value<*>>()
    fun getValue(name: String): Value<*>? {
        return values.find { it.name.equals(name, true) }
    }
}