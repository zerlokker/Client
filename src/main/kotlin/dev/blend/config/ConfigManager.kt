package dev.blend.config

import com.google.gson.GsonBuilder
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import dev.blend.Client
import dev.blend.module.api.ModuleManager
import dev.blend.util.IAccessor
import dev.blend.util.interfaces.IManager
import dev.blend.util.player.ChatUtil
import org.lwjgl.util.tinyfd.TinyFileDialogs
import java.io.File
import java.io.FileReader
import java.io.FileWriter

object ConfigManager: IAccessor, IManager {

    val configDir get() = File(clientDir, "configs")
    val gson = GsonBuilder()
        .setPrettyPrinting()
        .create()

    override fun initialize() {
        clientDir.mkdir()
        configDir.mkdir()
        load()
    }

    fun save(name: String = "default"): Boolean {
        try {
            val config = File(configDir, "$name.json")
            if (!config.exists()) {
                config.createNewFile()
            }
            FileWriter(config).use {
                val root = JsonObject()
                root.addProperty("name", Client.name.lowercase())
                root.addProperty("version", Client.version.lowercase())
                val modules = JsonArray()
                ModuleManager.modules.forEach { module ->
                    modules.add(module.getJsonObject())
                }
                root.add("modules", modules)
                it.write(gson.toJson(root))
            }
            return true
        } catch (e: Exception) {
            Client.logger.error("Failed to save config \"${name}\"", e)
            return false
        }
    }

    fun load(name: String = "default"): Boolean {
        val config = File(configDir, "$name.json")
        try {
            FileReader(config).use { reader ->
                val root = gson.fromJson(reader, JsonObject::class.java)
                val configVersion = root.get("version").asString.substring(2).toDouble()
                if (configVersion > Client.version.substring(2).toDouble()) {
                    with(ChatUtil) {
                        warn("Config ${aquafy(name)} saved on a newer version, Please update your client.")
                    }
                }
                val modules = root.getAsJsonArray("modules")
                modules.forEach { obj ->
                    val moduleName = obj.asJsonObject?.get("name")?.asString
                    ModuleManager.getModule(moduleName!!)?.useJsonObject(obj.asJsonObject)
                }
            }
            return true
        } catch (e: Exception) {
            Client.logger.error("Failed to load config \"${name}\"", e)
        }
        return false
    }

    fun listConfigs() {
        try {
            ChatUtil.info("List of available configs:")
            configDir.listFiles()?.forEach { file ->
                if (file.isFile) {
                    ChatUtil.info(file.name.replace(".json", ""), false)
                }
            }
        } catch (_: Exception) {
            ChatUtil.error("Error listing configs.")
        }
    }

}