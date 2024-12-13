package dev.blend.util

import dev.blend.Client
import net.minecraft.client.MinecraftClient
import java.io.File

interface IAccessor {
    val mc get() = MinecraftClient.getInstance()!!
    val clientDir get() = File(mc.runDirectory!!, Client.name)
}