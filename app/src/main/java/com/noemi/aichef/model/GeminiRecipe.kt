package com.noemi.aichef.model

import com.noemi.aichef.room.Ingredients
import com.noemi.aichef.room.Recipe
import com.noemi.aichef.room.Steps
import kotlinx.serialization.Serializable

@Serializable
data class GeminiRecipe(
    val name: String = "",
    val description: String = "",
    val ingredients: List<String> = emptyList(),
    val steps: List<String> = emptyList()
)

fun List<GeminiRecipe>.toRecipes(): List<Recipe> {
    val list = mutableListOf<Recipe>()
    this.map { gemini ->
        val recipe = Recipe(
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

