package dev.mateuy.decomposenavigationsample.ui.authenticated.countdown

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import dev.mateuy.decomposenavigationsample.ui.authenticated.countdown.CountDownComponent
import dev.mateuy.decomposenavigationsample.ui.authenticated.countdown.CountDownView
import dev.mateuy.decomposenavigationsample.ui.authenticated.countdown.ListComponent
import dev.mateuy.decomposenavigationsample.ui.authenticated.countdown.ListView
import dev.mateuy.decomposenavigationsample.ui.authenticated.screen2.Screen2Component
import dev.mateuy.decomposenavigationsample.ui.authenticated.screen2.Screen2View
import dev.mateuy.decomposenavigationsample.ui.authenticated.screen3.Screen3Component
import dev.mateuy.decomposenavigationsample.ui.authenticated.screen3.Screen3View

@Composable
fun CountDownHomeView(component: CountDownHomeComponent){
    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()){
        Children(component.childStack){
            when(val child = it.instance){
                is ItemsChildren.CountDownChild -> CountDownView(child.component)
                is ItemsChildren.ListChild -> ListView(child.component)
            }
        }
    }

}
