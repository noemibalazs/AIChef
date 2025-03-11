package com.noemi.aichef.model

import com.noemi.aichef.room.Ingredients
import com.noemi.aichef.room.Steps
import com.noemi.aichef.room.SuggestedRecipe
import kotlinx.serialization.Serializable

@Serializable
data class GeminiRecipe(
    val name: String = "",
    val description: String = "",
    val ingredients: List<String> = emptyList(),
    val steps: List<String> = emptyList()
)

fun List<GeminiRecipe>.toSuggestedRecipes(): List<SuggestedRecipe> {
    val list = mutableListOf<SuggestedRecipe>()
    this.map { gemini ->
        val recipe = SuggestedRecipe(
            name = gemini.name,
            description = gemini.description,
            ingredients = Ingredients(gemini.ingredients),
            steps = Steps(steps = gemini.steps),
            isFavorite = false,
            index = (0..2).random()
        )
        list.add(recipe)
    }

    return list
}

