package dev.mateuy.decomposenavigationsample.ui.authenticated.countdown

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.bringToFront
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.value.Value
import dev.mateuy.decomposenavigationsample.UseCases

import kotlinx.serialization.Serializable


sealed interface ItemsChildren{
    class ListChild(val component: ListComponent) : ItemsChildren
    class CountDownChild(val component: CountDownComponent) : ItemsChildren
}

class CountDownHomeComponent(
    componentContext: ComponentContext,
    val useCases: UseCases,
) : ComponentContext by componentContext {

    private val navigation = StackNavigation<Config>()
    val childStack: Value<ChildStack<*, ItemsChildren>> =
        childStack(
            source = navigation,
            serializer = Config.serializer(),
            initialConfiguration = Config.List,
            handleBackButton = true,
            childFactory = ::createChild,
        )

    private fun createChild(config: Config, childContext: ComponentContext): ItemsChildren =
        when (config) {
            is Config.List -> ItemsChildren.ListChild(itemList(childContext))
            is Config.CountDown -> ItemsChildren.CountDownChild(itemDetails(childContext, config))
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
    }
}