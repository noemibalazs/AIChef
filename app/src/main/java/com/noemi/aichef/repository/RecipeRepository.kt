package com.noemi.aichef.repository

import com.noemi.aichef.room.Recipe
import kotlinx.coroutines.flow.Flow

interface RecipeRepository {

    fun observeRecipes(): Flow<List<Recipe>>

    suspend fun insertRecipe(recipe: Recipe)

    suspend fun removeRecipe(recipe: Recipe)

   fun loadSuggestedRecipes(prompt: String): Flow<List<Recipe>>
}