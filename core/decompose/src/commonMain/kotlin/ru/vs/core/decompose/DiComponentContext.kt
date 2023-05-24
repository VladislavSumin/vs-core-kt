package ru.vs.core.decompose

import com.arkivanov.decompose.ComponentContext
import org.kodein.di.DIAware

/**
 * [ComponentContext] with included kodein di [DIAware]
 */
@Deprecated("Use constructor inject via factory")
interface DiComponentContext : ComponentContext, DIAware {
    companion object {
        /**
         * Created instance of [DiComponentContext] with given [ComponentContext] and [DIAware]
         */
        operator fun invoke(context: ComponentContext, di: DIAware): DiComponentContext {
            return object : DiComponentContext, ComponentContext by context, DIAware by di {}
        }
    }
}
