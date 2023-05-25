package ru.vs.core.decompose.router.list

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.decompose.router.children.SimpleNavigation
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.lifecycle.Lifecycle
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import com.arkivanov.essenty.lifecycle.resume
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals
import kotlin.test.assertSame

class ChildListFactoryTest {
    private val lifecycle = LifecycleRegistry()
    private val context = DefaultComponentContext(lifecycle)
    private val source = SimpleNavigation<List<Int>>()

    private lateinit var list: Value<List<TestComponentContext<Int>>>

    @BeforeEach
    fun setupEach() {
        lifecycle.onCreate()
        list = context.childList(
            source = source,
            childFactory = ::TestComponentContext
        )
    }

    @Test
    fun checkInitialConfigurationCreation() {
        source.navigate(CONFIG_1)
        val resultConfig = list.value.map { it.data }
        assertEquals(CONFIG_1, resultConfig)
    }

    @Test
    fun checkRemoveItem() {
        lifecycle.resume()
        source.navigate(CONFIG_1)
        val notChangingItem = list.value[1]
        val itemMovingToPreviousPosition = list.value[3]
        val removingItem = list.value[2]
        source.navigate(CONFIG_2)

        assertSame(notChangingItem, list.value[1])
        assertEquals(Lifecycle.State.RESUMED, notChangingItem.lifecycle.state)

        assertSame(itemMovingToPreviousPosition, list.value[2])
        assertEquals(Lifecycle.State.RESUMED, itemMovingToPreviousPosition.lifecycle.state)

        assertEquals(Lifecycle.State.DESTROYED, removingItem.lifecycle.state)
    }

    @Test
    fun checkAddItem() {
        lifecycle.resume()
        source.navigate(CONFIG_1)
        val notChangingItem = list.value[1]
        val itemMovingToNextPosition = list.value[3]
        source.navigate(CONFIG_3)

        assertSame(notChangingItem, list.value[1])
        assertEquals(Lifecycle.State.RESUMED, notChangingItem.lifecycle.state)

        assertSame(itemMovingToNextPosition, list.value[4])
        assertEquals(Lifecycle.State.RESUMED, itemMovingToNextPosition.lifecycle.state)

        val newItem = list.value[3]

        assertEquals(Lifecycle.State.RESUMED, newItem.lifecycle.state)
        assertEquals(5, newItem.data)
    }

    @Test
    fun checkChangeItem() {
        lifecycle.resume()
        source.navigate(CONFIG_1)
        val notChangingItem = list.value[1]
        val changingItem = list.value[2]
        source.navigate(CONFIG_4)

        assertSame(notChangingItem, list.value[1])
        assertEquals(Lifecycle.State.RESUMED, notChangingItem.lifecycle.state)

        val newItem = list.value[2]
        assertNotEquals(changingItem, newItem)

        assertEquals(Lifecycle.State.RESUMED, newItem.lifecycle.state)
        assertEquals(Lifecycle.State.DESTROYED, changingItem.lifecycle.state)
    }

    companion object {
        private val CONFIG_1 = listOf(1, 2, 3, 4) // default
        private val CONFIG_2 = listOf(1, 2, 4) // remove one
        private val CONFIG_3 = listOf(1, 2, 3, 5, 4) // add one
        private val CONFIG_4 = listOf(1, 2, 5, 4) // change one
    }
}

private class TestComponentContext<T : Any>(
    val data: T,
    context: ComponentContext
) : ComponentContext by context