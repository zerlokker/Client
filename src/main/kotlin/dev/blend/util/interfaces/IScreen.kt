package dev.blend.util.interfaces

interface IScreen {
    fun init()
    fun render(mouseX: Int, mouseY: Int)
    fun click(mouseX: Int, mouseY: Int, mouseButton: Int)
    fun release(mouseX: Int, mouseY: Int, mouseButton: Int)
    fun key(key: Int, scancode: Int, modifiers: Int)
    fun close()
}
