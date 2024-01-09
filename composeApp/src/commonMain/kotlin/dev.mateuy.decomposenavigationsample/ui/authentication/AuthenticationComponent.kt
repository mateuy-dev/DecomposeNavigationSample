package dev.mateuy.decomposenavigationsample.ui.authentication

import dev.mateuy.decomposenavigationsample.UseCases
import androidx.compose.runtime.mutableStateOf
import com.arkivanov.decompose.ComponentContext

class AuthenticationComponent(
    componentContext: ComponentContext,
    val useCases: UseCases
) : ComponentContext by componentContext {

    val username = mutableStateOf("")

    fun onAuthenticate(){
        useCases.login(username.value)
    }
}