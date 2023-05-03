package ru.vs.core.key_value_storage.service

import com.russhwolf.settings.ObservableSettings
import org.kodein.di.DirectDI

internal interface FlowSettingsFactory {
    fun createSettings(name: String): ObservableSettings
}

internal expect fun DirectDI.createSettingsFactory(): FlowSettingsFactory
