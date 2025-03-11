package com.noemi.aichef.util

import com.noemi.aichef.room.Ingredients
import com.noemi.aichef.room.Recipe
import com.noemi.aichef.room.Steps
import com.noemi.aichef.room.SuggestedRecipe

fun SuggestedRecipe.toRecipe() = Recipe(
    name = name,
    description = description,
    isFavorite = isFavorite,
    ingredients = Ingredients(ingredients = ingredients.ingredients),
    steps = Steps(steps.steps),
    index = index
)

fun Recipe.toSuggestedRecipe() = SuggestedRecipe(
    name = name,
    description = description,
    isFavorite = isFavorite,
    ingredients = Ingredients(ingredients = ingredients.ingredients),
    steps = Steps(steps.steps),
    index = index
)