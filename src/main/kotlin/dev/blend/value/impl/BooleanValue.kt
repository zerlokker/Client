package dev.blend.value.impl

import com.google.gson.JsonObject
import dev.blend.value.Value
import dev.blend.value.api.ValueHolder

class BooleanValue(
    name: String,
    parent: ValueHolder,
    defaultValue: Boolean,
    visibility: () -> Boolean = { true },
): Value<Boolean>(
    name,
    parent,
    defaultValue,
    visibility
) {
    fun toggle() {
        set(!value)
    }
    override fun getJsonObject(): JsonObject {
        val obj = JsonObject()
        obj.addProperty("name", name)
        obj.addProperty("value", value)
        return obj
    }
    override fun useJsonObject(jsonObject: JsonObject) {
        set(jsonObject.get("value").asBoolean)
    }
}