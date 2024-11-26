package dev.blend.util.render

import com.mojang.blaze3d.platform.GlStateManager
import com.mojang.blaze3d.systems.RenderSystem
import dev.blend.util.IAccessor
import dev.blend.util.misc.MiscUtil
import kotlinx.io.IOException
import org.lwjgl.nanovg.NVGColor
import org.lwjgl.nanovg.NanoVG.*
import org.lwjgl.nanovg.NanoVGGL3
import org.lwjgl.opengl.GL11
import org.lwjgl.system.MemoryStack
import java.awt.Color
import java.nio.ByteBuffer

object DrawUtil: IAccessor {

    private var context = -1L
    private lateinit var regularFont: ByteBuffer

    @JvmStatic
    fun initialize() {
        context = NanoVGGL3.nvgCreate(NanoVGGL3.NVG_ANTIALIAS or NanoVGGL3.NVG_STENCIL_STROKES)
        if (context == -1L) {
            throw IllegalStateException("NanoVG Context could not be created.")
        }
        try {
            regularFont = MiscUtil.getResourceAsByteBuffer("fonts/regular.ttf")
            nvgCreateFontMem(context, "regular", regularFont, false)
        } catch (_: IOException) {
            throw IllegalStateException("Fonts could not be initialized.")
        }
    }

    @JvmStatic
    fun begin() {
        preRender()
        nvgBeginFrame(context, mc.window.width.toFloat(), mc.window.height.toFloat(), 1.0f)
        save()
        scale()
    }
    @JvmStatic
    fun end() {
        restore()
        nvgEndFrame(context)
        postRender()
    }

    @JvmStatic
    fun save() = nvgSave(context)
    @JvmStatic
    fun restore() = nvgRestore(context)

    @JvmStatic
    fun scale() = scale(mc.window.scaleFactor)
    @JvmStatic
    fun scale(scaleFactor: Number) = scale(scaleFactor, scaleFactor)
    @JvmStatic
    fun scale(xScaleFactor: Number, yScaleFactor: Number) = nvgScale(context, xScaleFactor.toFloat(), yScaleFactor.toFloat())

    @JvmStatic
    fun translate(xTranslate: Number, yTranslate: Number) = nvgTranslate(context, xTranslate.toFloat(), yTranslate.toFloat())

    // Shapes.
    @JvmStatic
    fun rect(x: Number, y: Number, width: Number, height: Number, color: Color, alignment: Alignment = Alignment.TOP_LEFT) {
        nvgBeginPath(context)
        nvgShapeAntiAlias(context, true)
        NVGColor.calloc().use { nvgColor ->
            nvgRect(context, alignX(x, width, alignment), alignY(y, height, alignment), width.toFloat(), height.toFloat())
            nvgRGBAf(color.red / 255f, color.green / 255f, color.blue / 255f, color.alpha / 255f, nvgColor)
            nvgFillColor(context, nvgColor)
            nvgFill(context)
        }
        nvgClosePath(context)
    }
    @JvmStatic
    fun rectOutline(x: Number, y: Number, width: Number, height: Number, stroke: Number, color: Color, alignment: Alignment = Alignment.TOP_LEFT) {
        nvgBeginPath(context)
        nvgShapeAntiAlias(context, true)
        NVGColor.calloc().use { nvgColor ->
            nvgRect(context, alignX(x, width, alignment), alignY(y, height, alignment), width.toFloat(), height.toFloat())
            nvgRGBAf(color.red / 255f, color.green / 255f, color.blue / 255f, color.alpha / 255f, nvgColor)
            nvgStrokeWidth(context, stroke.toFloat())
            nvgStrokeColor(context, nvgColor)
            nvgStroke(context)
        }
        nvgClosePath(context)
    }

    @JvmStatic
    fun roundedRect(x: Number, y: Number, width: Number, height: Number, radius: Number, color: Color, alignment: Alignment = Alignment.TOP_LEFT) {
        nvgBeginPath(context)
        nvgShapeAntiAlias(context, true)
        NVGColor.calloc().use { nvgColor ->
            nvgRoundedRect(context, alignX(x, width, alignment), alignY(y, height, alignment), width.toFloat(), height.toFloat(), radius.toFloat())
            nvgRGBAf(color.red / 255f, color.green / 255f, color.blue / 255f, color.alpha / 255f, nvgColor)
            nvgFillColor(context, nvgColor)
            nvgFill(context)
        }
        nvgClosePath(context)
    }
    @JvmStatic
    fun roundedRect(x: Number, y: Number, width: Number, height: Number, radius: Number, stroke: Number, color: Color, alignment: Alignment = Alignment.TOP_LEFT) {
        nvgBeginPath(context)
        nvgShapeAntiAlias(context, true)
        NVGColor.calloc().use { nvgColor ->
            nvgRoundedRect(context, alignX(x, width, alignment), alignY(y, height, alignment), width.toFloat(), height.toFloat(), radius.toFloat())
            nvgRGBAf(color.red / 255f, color.green / 255f, color.blue / 255f, color.alpha / 255f, nvgColor)
            nvgStrokeWidth(context, stroke.toFloat())
            nvgStrokeColor(context, nvgColor)
            nvgStroke(context)
        }
        nvgClosePath(context)
    }

    // Font Rendering
    @JvmStatic
    fun drawString(text: String?, x: Number, y: Number, size: Number, color: Color) {
        drawString("regular", text!!, x, y, size, color)
    }
    @JvmStatic
    fun drawString(font: String, text: String, x: Number, y: Number, size: Number, color: Color, alignment: Alignment = Alignment.TOP_LEFT) {
        nvgBeginPath(context)
        nvgShapeAntiAlias(context, true)
        NVGColor.calloc().use { nvgColor ->
            nvgRGBAf(color.red / 255f, color.green / 255f, color.blue / 255f, color.alpha / 255f, nvgColor)
            nvgFillColor(context, nvgColor)
            nvgFontFace(context, font)
            nvgFontSize(context, size.toFloat())
            nvgTextAlign(context, getAlignFlags(alignment))
            nvgText(context, x.toFloat(), y.toFloat(), text)
        }
        nvgClosePath(context)
    }
    @JvmStatic
    fun getStringWidth(font: String, text: String, size: Number): Double {
        var width: Float
        nvgFontFace(context, font)
        nvgFontSize(context, size.toFloat())
        MemoryStack.stackPush().use { stack ->
            val bounds = floatArrayOf()
            nvgTextBounds(context, 0.0f, 0.0f, text, bounds)
            width = bounds[0] - bounds[2]
        }
        return width.toDouble()
    }

    private fun preRender() {
//        RenderSystem.enableBlend()
//        RenderSystem.blendFunc(GlStateManager.SrcFactor.SRC_ALPHA, GlStateManager.DstFactor.ONE)
//        RenderSystem.enableDepthTest();
//        RenderSystem.depthFunc(GL11.GL_LESS);
//        RenderSystem.clear(GL11.GL_DEPTH_BUFFER_BIT);
    }
    private fun postRender() {
//        RenderSystem.disableCull()
//        RenderSystem.disableDepthTest()
//        RenderSystem.enableBlend()
//        RenderSystem.blendFuncSeparate(GlStateManager.SrcFactor.SRC_ALPHA, GlStateManager.DstFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SrcFactor.ZERO, GlStateManager.DstFactor.ONE)
    }
    private fun alignX(x: Number, width: Number, alignment: Alignment): Float {
        return when (alignment) {
            Alignment.TOP_RIGHT, Alignment.CENTER_RIGHT, Alignment.BOTTOM_RIGHT -> x.toDouble() - width.toDouble()
            Alignment.TOP_CENTER, Alignment.CENTER, Alignment.BOTTOM_CENTER -> x.toDouble() - (width.toDouble() / 2.0)
            else -> x
        }.toFloat()
    }
    private fun alignY(y: Number, height: Number, alignment: Alignment): Float {
        return when (alignment) {
            Alignment.BOTTOM_LEFT, Alignment.BOTTOM_CENTER, Alignment.BOTTOM_RIGHT -> y.toDouble() - height.toDouble()
            Alignment.CENTER_LEFT, Alignment.CENTER, Alignment.CENTER_RIGHT -> y.toDouble() - (height.toDouble() / 2.0)
            else -> y
        }.toFloat()
    }
    private fun getAlignFlags(alignment: Alignment): Int {
        val x = when (alignment) {
            Alignment.TOP_CENTER, Alignment.CENTER, Alignment.BOTTOM_CENTER -> NVG_ALIGN_CENTER
            Alignment.TOP_RIGHT, Alignment.CENTER_RIGHT, Alignment.BOTTOM_RIGHT -> NVG_ALIGN_RIGHT
            else -> NVG_ALIGN_LEFT
        }
        val y = when (alignment) {
            Alignment.CENTER_LEFT, Alignment.CENTER, Alignment.CENTER_RIGHT -> NVG_ALIGN_MIDDLE
            Alignment.BOTTOM_LEFT, Alignment.BOTTOM_CENTER, Alignment.BOTTOM_RIGHT -> NVG_ALIGN_BOTTOM
            else -> NVG_ALIGN_TOP
        }
        return x or y
    }

}