package com.noemi.aichef.observer

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.noemi.aichef.repository.RecipeRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.launch
import javax.inject.Inject

class AppObserver @Inject constructor(
    private val repository: RecipeRepository,
    private val scope: CoroutineScope
) : DefaultLifecycleObserver {

    override fun onStop(owner: LifecycleOwner) {
        super.onStop(owner)
        scope.launch {
            println("Data base nuked...onStop()")
            repository.nukeSuggested()
        }
    }

    override fun onDestroy(owner: LifecycleOwner) {
        scope.launch {
            println("Data base nuked...onDelete()")
            repository.nukeSuggested()
        }

        super.onDestroy(owner)
    }
}