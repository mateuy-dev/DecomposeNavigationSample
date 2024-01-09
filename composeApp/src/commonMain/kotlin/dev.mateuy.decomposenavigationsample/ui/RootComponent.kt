package dev.mateuy.decomposenavigationsample.ui

import dev.mateuy.decomposenavigationsample.UseCases
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.slot.ChildSlot
import com.arkivanov.decompose.router.slot.SlotNavigation
import com.arkivanov.decompose.router.slot.activate
import com.arkivanov.decompose.router.slot.childSlot
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.lifecycle.coroutines.coroutineScope
import dev.mateuy.decomposenavigationsample.ui.authenticated.AuthenticatedComponent
import dev.mateuy.decomposenavigationsample.ui.authentication.AuthenticationComponent
import kotlinx.coroutines.launch

import kotlinx.serialization.Serializable

sealed interface RootChild {
    class AuthenticatedChild(val component: AuthenticatedComponent): RootChild
    class AuthenticationChild(val component: AuthenticationComponent): RootChild
    class SplashChild(val component: SplashComponent): RootChild
}

class RootComponent(
    componentContext: ComponentContext,
    useCases: UseCases = UseCases()
) : ComponentContext by componentContext {
    private val dialogNavigation = SlotNavigation<Config>()

    val children: Value<ChildSlot<*, RootChild>> =
        childSlot(
            source = dialogNavigation,
            serializer = Config.serializer(), // Or null to disable navigation state saving
            handleBackButton = false, // Close the dialog on back button press
            initialConfiguration = { Config.Splash }
        ) { config, childComponentContext ->
            when(config){
                Config.Authenticated -> RootChild.AuthenticatedChild(AuthenticatedComponent(childComponentContext, useCases))
                Config.Authentication -> RootChild.AuthenticationChild(AuthenticationComponent(childComponentContext, useCases))
                Config.Splash -> RootChild.SplashChild(SplashComponent(childComponentContext))
            }
        }

    init{
        coroutineScope().launch {
            useCases.loggedInFlow().collect{
                if(it){
                    dialogNavigation.activate(Config.Authenticated)
                } else {
                    dialogNavigation.activate(Config.Authentication)
                }
            }
        }
    }

    @Serializable
    private sealed class Config {
        @Serializable
        data object Authenticated : Config()
        @Serializable
        data object Authentication : Config()
        @Serializable
        data object Splash : Config()
    }
}