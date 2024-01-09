package dev.mateuy.decomposenavigationsample.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import dev.mateuy.decomposenavigationsample.ui.authenticated.AuthenticatedChild
import dev.mateuy.decomposenavigationsample.ui.authenticated.AuthenticatedComponent
import dev.mateuy.decomposenavigationsample.ui.authenticated.AuthenticatedView
import dev.mateuy.decomposenavigationsample.ui.authentication.AuthenticationComponent
import dev.mateuy.decomposenavigationsample.ui.authentication.AuthenticationView

@Composable
fun RootView(component: RootComponent){
    val dialogSlot by component.children.subscribeAsState()
    dialogSlot.child?.instance?.also { child ->
        when(child){
            is RootChild.AuthenticationChild -> AuthenticationView(child.component)
            is RootChild.AuthenticatedChild -> AuthenticatedView(child.component)
            is RootChild.SplashChild -> SplashView(child.component)
        }
    }

}