package ru.vs.core.decompose

import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.essenty.instancekeeper.InstanceKeeperDispatcher
import com.arkivanov.essenty.lifecycle.Lifecycle
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import com.arkivanov.essenty.lifecycle.destroy
import com.arkivanov.essenty.parcelable.ParcelableContainer
import com.arkivanov.essenty.statekeeper.StateKeeperDispatcher
import kotlin.test.assertNotEquals

abstract class BaseComponentTest {
    private var savedState = ParcelableContainer()
    private var stateKeeper = StateKeeperDispatcher(savedState)
    private val instanceKeeper = InstanceKeeperDispatcher()
    protected var lifecycle = LifecycleRegistry()
        private set
    protected var context = DefaultComponentContext(
        lifecycle = lifecycle,
        stateKeeper = stateKeeper,
        instanceKeeper = instanceKeeper,
    )
        private set

    open fun recreateContextWithSaveInstanceKeeper() {
        // Save state
        assertNotEquals(Lifecycle.State.DESTROYED, lifecycle.state)
        savedState = stateKeeper.save()
        lifecycle.destroy()

        // Create new instances
        stateKeeper = StateKeeperDispatcher(savedState)
        lifecycle = LifecycleRegistry()
        context = DefaultComponentContext(
            lifecycle = lifecycle,
            stateKeeper = stateKeeper,
            instanceKeeper = instanceKeeper,
        )
    }
}