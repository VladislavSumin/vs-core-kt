package ru.vs.core.mvi

import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import org.kodein.di.DI
import org.kodein.di.bindSingleton
import ru.vs.core.di.Modules

fun Modules.coreMvi() = DI.Module("core-mvi") {
    bindSingleton<StoreFactory> { DefaultStoreFactory() }
}
