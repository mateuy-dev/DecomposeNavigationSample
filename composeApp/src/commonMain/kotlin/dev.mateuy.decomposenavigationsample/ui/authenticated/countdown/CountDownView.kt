package dev.mateuy.decomposenavigationsample.ui.authenticated.countdown

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue

@Composable
fun CountDownView(component: CountDownComponent){
    val state by component.countDown.collectAsState()
    Column{
        Text("Seconds left in this modal screen:")
        Text(state.toString())
    }

}