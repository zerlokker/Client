package dev.blend.value.impl

import com.google.gson.JsonObject
import dev.blend.value.Value
import dev.blend.value.api.ValueHolder

class NumberValue(
    name: String,
    parent: ValueHolder,
    defaultValue: Number,
    visibility: () -> Boolean,
): Value<Number>(
    name, parent, defaultValue, visibility
) {
    constructor(name: String, parent: ValueHolder, defaultValue: Number): this(name, parent, defaultValue, { true })

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