package dev.mateuy.decomposenavigationsample

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

val currentUser : MutableStateFlow<String?> = MutableStateFlow(null)

/**
 * Dummy current user repository
 */
class UseCases {
    fun loggedInFlow() : Flow<Boolean> = currentUser.map { it!=null }
    fun login(value: String) {
        currentUser.value = value
    }
    fun countDown(start : Int) = flow {
        (start downTo 0).forEach {
            emit(it)
            if(it!=0)
                delay(1000)
        }
    }
}