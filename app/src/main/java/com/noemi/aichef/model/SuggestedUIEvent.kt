package com.noemi.aichef.model

import com.noemi.aichef.room.SuggestedRecipe

sealed class SuggestedUIEvent {
    data object Search : SuggestedUIEvent()
    data object Disapprove : SuggestedUIEvent()
    data class StateChanged(val recipe: SuggestedRecipe) : SuggestedUIEvent()
    data object Observe : SuggestedUIEvent()
}