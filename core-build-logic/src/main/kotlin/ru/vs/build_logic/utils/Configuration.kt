package ru.vs.build_logic.utils

import kotlin.reflect.KClass

@Suppress("UnnecessaryAbstractClass")
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

        @Suppress("UNCHECKED_CAST")
        return when (kClass) {
            String::class -> rawProperty
            Boolean::class -> rawProperty.toBoolean()
            Integer::class -> rawProperty.toInt()
            else -> Error("Unsupported cast to ${kClass.simpleName}")
        } as T
    }
}

fun interface PropertyProvider {
    /**
     * Provides property value by property name
     * @param name property name
     * @return property value or null if property not exists
     */
    fun getProperty(name: String): String?
}
