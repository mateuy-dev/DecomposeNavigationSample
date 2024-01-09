package dev.mateuy.decomposenavigationsample.ui.authentication

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun AuthenticationView(component: AuthenticationComponent){

    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxSize()) {
        TextField(value = component.username.value,
            onValueChange = {component.username.value = it})
        Button(onClick = component::onAuthenticate){
            Text("Login")
        }
    }
}