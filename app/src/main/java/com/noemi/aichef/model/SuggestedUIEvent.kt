package com.noemi.aichef.model

import com.noemi.aichef.room.Recipe

sealed class SuggestedUIEvent {
    data object Search : SuggestedUIEvent()
    data object Disapprove : SuggestedUIEvent()
    data class StateChanged(val recipe: Recipe) : SuggestedUIEvent()
}