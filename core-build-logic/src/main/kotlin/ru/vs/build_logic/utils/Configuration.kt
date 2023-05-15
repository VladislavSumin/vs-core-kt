package ru.vs.build_logic.utils

import kotlin.reflect.KClass

abstract class Configuration(
    private val basePath: String,
    private val propertyProvider: PropertyProvider,
) {
    constructor(relativePath: String, parent: Configuration) : this(
        "${parent.basePath}.$relativePath",
        parent.propertyProvider
    )

    protected inline fun <reified T : Any> property(relativePath: String, defaultValue: T): T =
        propertyOrNull(relativePath, T::class) ?: defaultValue

    protected inline fun <reified T : Any> property(relativePath: String): T =
        propertyOrNull(relativePath, T::class)!!

    protected inline fun <reified T : Any> propertyOrNull(relativePath: String): T? =
        propertyOrNull(relativePath, T::class)


    protected fun <T : Any> propertyOrNull(relativePath: String, kClass: KClass<T>): T? {

        val path = if (basePath.isEmpty()) relativePath
        else if (relativePath.isEmpty()) basePath
        else "$basePath.$relativePath"

        val rawProperty = propertyProvider.getProperty(path) ?: return null
        return when (kClass) {
            String::class -> rawProperty
            Boolean::class -> rawProperty.toBoolean()
            Integer::class -> rawProperty.toInt()
            else -> Error("Unsupported cast to ${kClass.simpleName}")
        } as T
    }
}

fun interface PropertyProvider {
    fun getProperty(name: String): String?
}