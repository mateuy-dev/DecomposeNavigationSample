package dev.mateuy.decomposenavigationsample.ui.authenticated

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.stack.Children
import dev.mateuy.decomposenavigationsample.ui.authenticated.countdown.CountDownHomeView
import dev.mateuy.decomposenavigationsample.ui.authenticated.screen2.Screen2View
import dev.mateuy.decomposenavigationsample.ui.authenticated.screen3.Screen3View

@Composable
fun AuthenticatedView(component: AuthenticatedComponent){
    val state by component.state.collectAsState()

    if(!state.modalScreen) {
        Scaffold(bottomBar = {
            BottomAppBar(actions = {
                IconButton(
                    onClick = component::goToCountDownScreen
                ) {
                    Icon(imageVector = Icons.Default.Home, contentDescription = "Home")
                }

                IconButton(
                    onClick = component::goToScreen2
                ) {
                    Icon(imageVector = Icons.Default.Search, contentDescription = "Search")
                }

                IconButton(
                    onClick = component::goToScreen3
                ) {
                    Icon(imageVector = Icons.Default.AccountCircle, contentDescription = "Profile")
                }
            })
        }) {
            AuthenticatedViewContent(state)
        }
    } else {
        AuthenticatedViewContent(state)
    }

}

@Composable
fun AuthenticatedViewContent(state: AuthenticatedState) {
    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()){
        Children(state.childStack){
            when(val child = it.instance){
                is AuthenticatedChildren.CountDownHomeChild -> CountDownHomeView(child.component)
                is AuthenticatedChildren.Screen2Child -> Screen2View(child.component)
                is AuthenticatedChildren.Screen3Child -> Screen3View(child.component)
            }
        }
    }

}
