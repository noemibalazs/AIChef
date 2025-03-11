package com.noemi.aichef.repository

import com.noemi.aichef.room.Recipe
import com.noemi.aichef.room.SuggestedRecipe
import kotlinx.coroutines.flow.Flow

interface RecipeRepository {

    fun observeRecipes(): Flow<List<Recipe>>

    suspend fun insertRecipe(recipe: Recipe)

    suspend fun removeRecipe(recipe: Recipe)

   fun loadSuggestedRecipes(prompt: String): Flow<List<SuggestedRecipe>>

    fun observeSuggestedRecipes(): Flow<List<SuggestedRecipe>>

    suspend fun insertSuggestedRecipes(recipe: List<SuggestedRecipe>)

    suspend fun updateSuggestedRecipe(recipe: SuggestedRecipe)

    suspend fun nukeSuggested()
}