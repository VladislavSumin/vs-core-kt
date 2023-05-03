package ru.vs.core.key_value_storage.service

import com.russhwolf.settings.ObservableSettings
import com.russhwolf.settings.StorageSettings
import org.kodein.di.DirectDI

private class FlowSettingsFactoryImpl : FlowSettingsFactory {
    private val factory = StorageSettings()

    override fun createSettings(name: String): ObservableSettings {
        return TODO("Not implemented")
    }
}

internal actual fun DirectDI.createSettingsFactory(): FlowSettingsFactory {
    return FlowSettingsFactoryImpl()
}