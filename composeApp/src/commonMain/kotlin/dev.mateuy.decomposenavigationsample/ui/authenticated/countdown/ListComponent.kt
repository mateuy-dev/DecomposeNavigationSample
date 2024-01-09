package dev.mateuy.decomposenavigationsample.ui.authenticated.countdown

import com.arkivanov.decompose.ComponentContext

import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import com.arkivanov.essenty.lifecycle.coroutines.*
import dev.mateuy.decomposenavigationsample.ui.authenticated.AuthenticatedChild
import kotlinx.coroutines.flow.flowOf

class ListComponent(
    componentContext: ComponentContext,
    val onItemSelected: (Int) -> Unit,
) : ComponentContext by componentContext {
    val list = flowOf(List(10){it+5}).stateIn(coroutineScope(), SharingStarted.WhileSubscribed(), listOf())

}