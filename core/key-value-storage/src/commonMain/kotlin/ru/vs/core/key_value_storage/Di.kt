package ru.vs.core.key_value_storage

import org.kodein.di.DI
import org.kodein.di.bindSingleton
import ru.vs.core.di.Modules
import ru.vs.core.di.i
import ru.vs.core.key_value_storage.service.KeyValueStorageService
import ru.vs.core.key_value_storage.service.KeyValueStorageServiceImpl
import ru.vs.core.key_value_storage.service.createSettingsFactory

fun Modules.coreKeyValueStorage() = DI.Module("core-key-value-storage") {
    bindSingleton { createSettingsFactory() }
    bindSingleton<KeyValueStorageService> { KeyValueStorageServiceImpl(i()) }
}
