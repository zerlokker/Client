package dev.blend.value.impl

import com.google.gson.JsonObject
import dev.blend.value.Value
import dev.blend.value.api.ValueHolder

class ModeValue(
    name: String,
    parent: ValueHolder,
    modes: Array<String>,
    visibility: () -> Boolean = { true },
): Value<String>(
    name, parent, modes.first(), visibility
) {

    val modes = mutableListOf<String>()

    init {
        modes.forEach {
            add(it)
        }
    }

    fun add(mode: String): ModeValue {
        if (!modes.contains(mode)) {
            modes.add(mode)
        }
        return this
    }

    fun `is`(value: String): Boolean {
        return this.value.equals(value, true)
    }
    fun isNot(value: String): Boolean {
        return !`is`(value)
    }

    override fun set(value: String) {
        if (modes.contains(value)) {
            super.set(value)
        }
    }

    override fun getJsonObject(): JsonObject {
        val obj = JsonObject()
        obj.addProperty("name", name)
        obj.addProperty("value", value)
        return obj
    }

    override fun useJsonObject(jsonObject: JsonObject) {
        set(jsonObject.get("value").asString)
    }
}