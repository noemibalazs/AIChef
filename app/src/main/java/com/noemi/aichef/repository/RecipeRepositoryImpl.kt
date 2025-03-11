package com.noemi.aichef.repository

import com.noemi.aichef.model.GeminiRecipe
import com.noemi.aichef.model.RecipeNumber
import com.noemi.aichef.model.RecipeNumber.Companion.toName
import com.noemi.aichef.model.toSuggestedRecipes
import com.noemi.aichef.room.Recipe
import com.noemi.aichef.room.RecipeDAO
import com.noemi.aichef.room.SuggestedDAO
import com.noemi.aichef.room.SuggestedRecipe
import com.noemi.aichef.service.RecipeService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.Json
import javax.inject.Inject

class RecipeRepositoryImpl @Inject constructor(
    private val recipeDAO: RecipeDAO,
    private val suggestedDAO: SuggestedDAO,
    private val recipeService: RecipeService,
    private val json: Json
) : RecipeRepository {

    override fun observeRecipes(): Flow<List<Recipe>> = recipeDAO.observeRecipes()

    override suspend fun insertRecipe(recipe: Recipe) = recipeDAO.saveRecipe(recipe)

    override suspend fun removeRecipe(recipe: Recipe) = recipeDAO.deleteRecipe(recipe)

    override fun loadSuggestedRecipes(prompt: String): Flow<List<SuggestedRecipe>> = flow {
        val response = recipeService.getSuggestedRecipes(prompt, RecipeNumber.THREE.toName())

        val parts = response.candidates[0].content.parts
        val text = parts[0].text

        val geminiRecipes = parseResponseJson(text)
        val recipes = geminiRecipes.toSuggestedRecipes()

        emit(recipes)
    }

    override fun observeSuggestedRecipes(): Flow<List<SuggestedRecipe>> = suggestedDAO.observeSuggestedRecipes()

    override suspend fun insertSuggestedRecipes(recipe: List<SuggestedRecipe>) = suggestedDAO.saveSuggestedRecipes(recipe)

    override suspend fun updateSuggestedRecipe(recipe: SuggestedRecipe) = suggestedDAO.updateSuggestedRecipe(recipe)

    override suspend fun nukeSuggested() = suggestedDAO.deleteSuggestedRecipe()

    private fun parseResponseJson(text: String): List<GeminiRecipe> {
        val startIndex = text.indexOfFirst { ch -> ch == '[' }
        val lastIndex = text.lastIndexOf(']')
        val clearedString = text.substring(startIndex, lastIndex + 1)

        return try {
            json.decodeFromString(ListSerializer(GeminiRecipe.serializer()), clearedString)
        } catch (e: Exception) {
            println("Json parsing exception was thrown, see ${e.message}")
            emptyList()
        }
    }
}