package dev.blend.value

import com.google.gson.JsonObject
import dev.blend.value.api.ValueHolder

abstract class Value<T>(
    val name: String,
    val parent: ValueHolder,
    private val defaultValue: T,
    var visibility: () -> Boolean,
) {
    protected var value = defaultValue
    open fun get(): T {
        return value
    }
    open fun set(value: T) {
        this.value = value
    }
    fun reset() {
        set(defaultValue)
    }
    abstract fun getJsonObject(): JsonObject
    abstract fun useJsonObject(jsonObject: JsonObject)
}