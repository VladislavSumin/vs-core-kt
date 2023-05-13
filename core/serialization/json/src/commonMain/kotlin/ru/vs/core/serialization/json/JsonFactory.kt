package ru.vs.core.serialization.json

import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.plus

interface JsonFactory {
    fun createDefault(): Json
}

/**
 * @param serializersModulesSet - set of global [SerializersModule], using to create [createDefault] json instance
 */
internal class JsonFactoryImpl(
    private val serializersModulesSet: Set<SerializersModule>
) : JsonFactory {

    private val mergedModule: SerializersModule by lazy {
        /**
         * Concatenate all modules from [serializersModulesSet] into one [SerializersModule]
         */
        serializersModulesSet.fold(null as SerializersModule?) { m1, m2 ->
            m1?.plus(m2) ?: m2
        } ?: SerializersModule { }
    }

    override fun createDefault() = Json {
//        encodeDefaults = true
//        isLenient = true
//        allowSpecialFloatingPointValues = true
//        allowStructuredMapKeys = true
//        prettyPrint = true
//        useArrayPolymorphism = false
        serializersModule = mergedModule
    }
}
