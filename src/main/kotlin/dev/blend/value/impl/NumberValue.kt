package dev.blend.value.impl

import com.google.gson.JsonObject
import dev.blend.value.Value
import dev.blend.value.api.ValueHolder
import kotlin.math.*

class NumberValue(
    name: String,
    parent: ValueHolder,
    defaultValue: Number,
    val min: Number,
    val max: Number,
    val increment: Number,
    visibility: () -> Boolean = { true },
): Value<Number>(
    name, parent, defaultValue, visibility
) {

    override fun set(value: Number) {
        val precision = 1 / increment.toDouble()
        super.set(max(min.toDouble(), min(max.toDouble(), round(value.toDouble() * precision) / precision)))
    }

    override fun toString(): String {
        return if (increment.toDouble() % 1.0 == 0.0) {
            value.toInt().toString()
        } else {
            value.toDouble().toString()
        }
    }

    override fun getJsonObject(): JsonObject {
        val obj = JsonObject()
        obj.addProperty("name", name)
        obj.addProperty("value", value)
        return obj
    }

    override fun useJsonObject(jsonObject: JsonObject) {
        set(jsonObject.get("value").asNumber)
    }
}