package ru.vs.core.decompose.router.list

import com.arkivanov.decompose.router.children.SimpleNavigation
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.lifecycle.Lifecycle
import com.arkivanov.essenty.lifecycle.resume
import kotlinx.coroutines.flow.StateFlow
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import ru.vs.core.decompose.BaseComponentTest
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals
import kotlin.test.assertSame

class ChildListWithStateFactoryTest : BaseComponentTest() {
    private val source = SimpleNavigation<List<Int>>()

    private lateinit var list: Value<List<TestComponentContext<StateFlow<Int>>>>

    @BeforeEach
    fun setupEach() {
        lifecycle.onCreate()
        list = context.childListWithState(
            source = source,
            idSelector = { it / 10 },
            childFactory = ::TestComponentContext
        )
    }

    @Test
    fun checkInitialConfigurationCreation() {
        source.navigate(CONFIG_1)
        val resultConfig = list.value.map { it.data.value }
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
        assertEquals(50, newItem.data.value)
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

    @Test
    fun checkMoveItem() {
        lifecycle.resume()
        source.navigate(CONFIG_1)
        val movingItem = list.value[2]
        source.navigate(CONFIG_5)

        val movedItem = list.value[1]
        assertSame(movingItem, movedItem)
        assertEquals(Lifecycle.State.RESUMED, movedItem.lifecycle.state)
    }

    @Test
    fun checkChangeItemState() {
        lifecycle.resume()
        source.navigate(CONFIG_1)
        val changingItem = list.value[1]
        assertEquals(20, changingItem.data.value)
        source.navigate(CONFIG_6)

        val changedItem = list.value[1]
        assertSame(changingItem, changedItem)
        assertEquals(Lifecycle.State.RESUMED, changedItem.lifecycle.state)
        assertEquals(22, changedItem.data.value)
    }

    companion object {
        /**
         * First digit is id,
         * two digit in pair is value
         */
        private val CONFIG_1 = listOf(10, 20, 30, 40) // default
        private val CONFIG_2 = listOf(10, 20, 40) // remove one
        private val CONFIG_3 = listOf(10, 20, 30, 50, 40) // add one
        private val CONFIG_4 = listOf(10, 20, 50, 40) // change one
        private val CONFIG_5 = listOf(10, 30, 20, 40) // move one

        private val CONFIG_6 = listOf(10, 22, 30, 40) // change state

    }
}