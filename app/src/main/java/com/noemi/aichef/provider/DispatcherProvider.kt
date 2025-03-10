package com.noemi.aichef.provider

import kotlinx.coroutines.CoroutineDispatcher

interface DispatcherProvider {

    fun io(): CoroutineDispatcher
    fun default(): CoroutineDispatcher
    fun main(): CoroutineDispatcher
}