package com.noemi.aichef.model

import com.noemi.aichef.room.Recipe

sealed class RecipeDetailsUIEvent {
    data class StateChanged(val recipe: Recipe) : RecipeDetailsUIEvent()
}