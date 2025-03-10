package com.noemi.aichef.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel<Event> : ViewModel() {

    private val event = MutableSharedFlow<Event>()

    init {
        viewModelScope.launch {
            event.collect {
                handleEvent(it)
            }
        }
    }

    abstract fun handleEvent(event: Event)

    fun onEvent(onEvent: Event) {
        viewModelScope.launch {
            event.emit(onEvent)
        }
    }
}