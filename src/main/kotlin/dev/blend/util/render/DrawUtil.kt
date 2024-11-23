package dev.blend.util.render

import com.mojang.blaze3d.platform.GlStateManager
import com.mojang.blaze3d.systems.RenderSystem
import dev.blend.util.IAccessor
import org.lwjgl.nanovg.NanoVG.*
import org.lwjgl.nanovg.NanoVGGL3
import org.lwjgl.opengl.GL11

object DrawUtil: IAccessor {

    private var context = -1L

    @JvmStatic
    fun initialize() {
        context = NanoVGGL3.nvgCreate(NanoVGGL3.NVG_ANTIALIAS and NanoVGGL3.NVG_STENCIL_STROKES)
        if (context == -1L) {
            throw IllegalStateException("NanoVG Context could not be created.")
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

    private fun preRender() {
        RenderSystem.enableBlend()
        RenderSystem.blendFunc(GlStateManager.SrcFactor.SRC_ALPHA, GlStateManager.DstFactor.ONE)
        RenderSystem.enableDepthTest()
        RenderSystem.depthFunc(GL11.GL_LESS)
        RenderSystem.clear(GL11.GL_DEPTH_BUFFER_BIT)
    }
    private fun postRender() {
        RenderSystem.disableCull()
        RenderSystem.disableDepthTest()
        RenderSystem.enableBlend()
        RenderSystem.blendFuncSeparate(GlStateManager.SrcFactor.SRC_ALPHA, GlStateManager.DstFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SrcFactor.ZERO, GlStateManager.DstFactor.ONE)
    }

}