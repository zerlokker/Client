package dev.blend.util.render

import org.lwjgl.nanovg.NanoVGGL3

object DrawUtil {

    private var context = -1L

    @JvmStatic
    fun initialize() {
        context = NanoVGGL3.nvgCreate(NanoVGGL3.NVG_ANTIALIAS and NanoVGGL3.NVG_STENCIL_STROKES)
        if (context == -1L) {
            throw IllegalStateException("NanoVG Context could not be created.")
        }
    }

}