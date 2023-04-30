package ru.vs.core.serialization.json

import org.kodein.di.DI
import org.kodein.di.bindSingleton
import ru.vs.core.di.Modules
import ru.vs.core.di.i

fun Modules.coreSerializationJson() = DI.Module("core-serialization-json") {
    // bindSet<SerializersModule>()

    bindSingleton<JsonFactory> { JsonFactoryImpl() }
    bindSingleton { i<JsonFactory>().createDefault() }
}
