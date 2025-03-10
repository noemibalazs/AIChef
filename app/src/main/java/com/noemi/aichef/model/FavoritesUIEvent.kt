package com.noemi.aichef.model

import com.noemi.aichef.room.Recipe

sealed class FavoritesUIEvent {

    data object Display : FavoritesUIEvent()
    data class Delete(val recipe: Recipe) : FavoritesUIEvent()
}