package ru.vs.core.key_value_storage.service

import com.russhwolf.settings.NSUserDefaultsSettings
import com.russhwolf.settings.ObservableSettings
import org.kodein.di.DirectDI

private class FlowSettingsFactoryImpl : FlowSettingsFactory {
    private val factory = NSUserDefaultsSettings.Factory()

    override fun createSettings(name: String): ObservableSettings {
        return factory.create(name)
    }
}

internal actual fun DirectDI.createSettingsFactory(): FlowSettingsFactory {
    return FlowSettingsFactoryImpl()
}