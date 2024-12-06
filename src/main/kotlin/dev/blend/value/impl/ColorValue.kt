package dev.blend.value.impl

import com.google.gson.JsonObject
import dev.blend.value.Value
import dev.blend.value.api.ValueHolder
import java.awt.Color

class ColorValue(
    name: String,
    parent: ValueHolder,
    defaultValue: Color,
    visibility: () -> Boolean = { true },
): Value<Color>(
    name, parent, defaultValue, visibility
) {
    var hue: Float = 0.0f
    var saturation: Float = 0.0f
    var brightness: Float = 1.0f

    init {
        set(defaultValue)
    }

    fun set(hsb: FloatArray) {
        set(hsb[0], hsb[1], hsb[2])
    }

    fun set(hue: Float, saturation: Float, brightness: Float) {
        this.hue = hue
        this.saturation = saturation
        this.brightness = brightness
    }

    override fun get(): Color {
        return Color.getHSBColor(hue, saturation, brightness)
    }
    override fun set(value: Color) {
        val hsb = floatArrayOf(1f, 1f, 1f)
        set(Color.RGBtoHSB(value.red, value.green, value.blue, hsb))
    }
    override fun getJsonObject(): JsonObject {
        val obj = JsonObject()
        obj.addProperty("name", name)
        obj.addProperty("hue", hue)
        obj.addProperty("saturation", saturation)
        obj.addProperty("brightness", brightness)
        return obj
    }

    override fun useJsonObject(jsonObject: JsonObject) {
        set(floatArrayOf(
            jsonObject.get("hue").asFloat,
            jsonObject.get("saturation").asFloat,
            jsonObject.get("brightness").asFloat,
        ))
    }
}