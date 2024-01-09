package dev.mateuy.decomposenavigationsample.ui.authenticated.countdown

import com.arkivanov.decompose.ComponentContext

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import com.arkivanov.essenty.lifecycle.coroutines.*
import dev.mateuy.decomposenavigationsample.UseCases
import dev.mateuy.decomposenavigationsample.ui.authenticated.AuthenticatedChild
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.WhileSubscribed
import kotlinx.coroutines.flow.onCompletion

class CountDownComponent(
    start: Int,
    componentContext: ComponentContext,
    useCases: UseCases,
    onFinished: () -> Unit,
) : ComponentContext by componentContext {

    val countDown = useCases.countDown(start).onCompletion { e -> if(e !is CancellationException) onFinished() }
        .stateIn(coroutineScope(), SharingStarted.WhileSubscribed(), 1000)

}