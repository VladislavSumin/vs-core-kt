package ru.vs.core.key_value_storage.service

import com.russhwolf.settings.ObservableSettings
import org.kodein.di.DirectDI

private class FlowSettingsFactoryImpl : FlowSettingsFactory {

    override fun createSettings(name: String): ObservableSettings {
        return TODO()
    }
}

internal actual fun DirectDI.createSettingsFactory(): FlowSettingsFactory {
    return FlowSettingsFactoryImpl()
}