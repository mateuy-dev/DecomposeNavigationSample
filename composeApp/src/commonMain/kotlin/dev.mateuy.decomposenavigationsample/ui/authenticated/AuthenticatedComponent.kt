package dev.mateuy.decomposenavigationsample.ui.authenticated

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.bringToFront
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.essenty.lifecycle.coroutines.coroutineScope
import dev.mateuy.decomposenavigationsample.UseCases
import dev.mateuy.decomposenavigationsample.decomposeutils.toStateFlow
import dev.mateuy.decomposenavigationsample.ui.authenticated.countdown.CountDownHomeComponent
import dev.mateuy.decomposenavigationsample.ui.authenticated.countdown.ItemsChildren
import dev.mateuy.decomposenavigationsample.ui.authenticated.screen2.Screen2Component
import dev.mateuy.decomposenavigationsample.ui.authenticated.screen3.Screen3Component
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

import kotlinx.serialization.Serializable

sealed interface AuthenticatedChild

sealed interface AuthenticatedChildren{
    class CountDownHomeChild(val component: CountDownHomeComponent) : AuthenticatedChildren
    class Screen2Child(val component: Screen2Component) : AuthenticatedChildren
    class Screen3Child(val component: Screen3Component) : AuthenticatedChildren
}

class AuthenticatedState(val modalScreen: Boolean, val childStack: ChildStack<*, AuthenticatedChildren>)

class AuthenticatedComponent(
    componentContext: ComponentContext,
    val useCases: UseCases,
) : ComponentContext by componentContext {

    private val navigation = StackNavigation<Config>()
    private val childStack: StateFlow<ChildStack<*, AuthenticatedChildren>> =
        childStack(
            source = navigation,
            serializer = Config.serializer(), // Or null to disable navigation state saving
            initialConfiguration = Config.CountDownHome,
            handleBackButton = true, // Pop the back stack on back button press
            childFactory = ::createChild,
        ).toStateFlow()

    private val modalScreen = childStack.flatMapConcat {
        val current = it.active.instance
        if(current is AuthenticatedChildren.CountDownHomeChild)
            current.component.childStack.toStateFlow().map { it.active.instance is ItemsChildren.CountDownChild }
        else
            flowOf(false)
    }

    val state = combine(modalScreen, childStack, ::AuthenticatedState)
        .stateIn(coroutineScope(), SharingStarted.WhileSubscribed(), AuthenticatedState(false, childStack.value))

    fun goToCountDownScreen() = navigation.bringToFront(Config.CountDownHome)
    fun goToScreen2() = navigation.bringToFront(Config.Screen2)
    fun goToScreen3() = navigation.bringToFront(Config.Screen3)

    private fun createChild(config: Config, childContext: ComponentContext): AuthenticatedChildren =
        when (config) {
            Config.CountDownHome -> AuthenticatedChildren.CountDownHomeChild(CountDownHomeComponent(childContext, useCases))
            Config.Screen2 -> AuthenticatedChildren.Screen2Child(Screen2Component(childContext))
            Config.Screen3 -> AuthenticatedChildren.Screen3Child(Screen3Component(childContext))
        }


    @Serializable
    private sealed class Config {
        @Serializable
        data object CountDownHome : Config()
        @Serializable
        data object Screen2 : Config()
        @Serializable
        data object Screen3 : Config()

    }
}