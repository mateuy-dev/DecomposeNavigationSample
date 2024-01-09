package dev.mateuy.decomposenavigationsample.ui.authenticated

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.bringToFront
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.push
import com.arkivanov.decompose.router.stack.replaceCurrent
import com.arkivanov.decompose.value.Value
import dev.mateuy.decomposenavigationsample.UseCases
import dev.mateuy.decomposenavigationsample.ui.authenticated.countdown.CountDownComponent
import dev.mateuy.decomposenavigationsample.ui.authenticated.countdown.ListComponent
import dev.mateuy.decomposenavigationsample.ui.authenticated.screen2.Screen2Component
import dev.mateuy.decomposenavigationsample.ui.authenticated.screen3.Screen3Component

import kotlinx.serialization.Serializable

sealed interface AuthenticatedChild

sealed interface AuthenticatedChildren{
    class ListChild(val component: ListComponent) : AuthenticatedChildren
    class CountDownChild(val component: CountDownComponent) : AuthenticatedChildren
    class Screen2Child(val component: Screen2Component) : AuthenticatedChildren
    class Screen3Child(val component: Screen3Component) : AuthenticatedChildren
}

class AuthenticatedComponent(
    componentContext: ComponentContext,
    val useCases: UseCases,
) : ComponentContext by componentContext {

    private val navigation = StackNavigation<Config>()
    val childStack: Value<ChildStack<*, AuthenticatedChildren>> =
        childStack(
            source = navigation,
            serializer = Config.serializer(), // Or null to disable navigation state saving
            initialConfiguration = Config.List,
            handleBackButton = true, // Pop the back stack on back button press
            childFactory = ::createChild,
        )

    fun goToCountDownScreen() = navigation.bringToFront(Config.List)
    fun goToScreen2() = navigation.bringToFront(Config.Screen2)
    fun goToScreen3() = navigation.bringToFront(Config.Screen3)

    private fun createChild(config: Config, childContext: ComponentContext): AuthenticatedChildren =
        when (config) {
            is Config.List -> AuthenticatedChildren.ListChild(itemList(childContext))
            is Config.CountDown -> AuthenticatedChildren.CountDownChild(itemDetails(childContext, config))
            Config.Screen2 -> AuthenticatedChildren.Screen2Child(Screen2Component(childContext))
            Config.Screen3 -> AuthenticatedChildren.Screen3Child(Screen3Component(childContext))
        }

    private fun itemList(componentContext: ComponentContext): ListComponent =
        ListComponent(
            componentContext = componentContext
        ) { navigation.bringToFront(Config.CountDown(it)) }

    private fun itemDetails(componentContext: ComponentContext, config: Config.CountDown): CountDownComponent =
        CountDownComponent(
            componentContext = componentContext,
            start = config.start,
                    useCases = useCases
        )
        { navigation.pop() }

    @Serializable
    private sealed class Config {
        @Serializable
        data object List : Config()
        @Serializable
        data class CountDown(val start: Int) : Config()
        @Serializable
        data object Screen2 : Config()
        @Serializable
        data object Screen3 : Config()

    }
}