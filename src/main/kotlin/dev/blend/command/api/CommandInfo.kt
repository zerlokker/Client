package dev.blend.command.api

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class CommandInfo(
    val names: Array<String>,
    val description: String,
    val syntax: Array<String>
)
