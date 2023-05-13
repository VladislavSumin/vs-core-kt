package ru.vs.core.serialization.json

import kotlinx.serialization.modules.SerializersModule
import org.kodein.di.DI
import org.kodein.di.bindSet
import org.kodein.di.bindSingleton
import ru.vs.core.di.Modules
import ru.vs.core.di.i

fun Modules.coreSerializationJson() = DI.Module("core-serialization-json") {
    /**
     * You can add [SerializersModule] binding to here,
     * it's passing to [JsonFactory] and using to create default Json instance
     */
    bindSet<SerializersModule>()

    bindSingleton<JsonFactory> { JsonFactoryImpl(i()) }
    bindSingleton { i<JsonFactory>().createDefault() }
}
