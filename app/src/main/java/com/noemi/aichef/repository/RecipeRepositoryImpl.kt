package com.noemi.aichef.repository

import com.noemi.aichef.model.GeminiRecipe
import com.noemi.aichef.model.RecipeNumber
import com.noemi.aichef.model.RecipeNumber.Companion.toName
import com.noemi.aichef.model.toRecipes
import com.noemi.aichef.room.Recipe
import com.noemi.aichef.room.RecipeDAO
import com.noemi.aichef.service.RecipeService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.Json
import javax.inject.Inject

class RecipeRepositoryImpl @Inject constructor(
    private val recipeDAO: RecipeDAO,
    private val recipeService: RecipeService,
    private val json: Json
) : RecipeRepository {

    override fun observeRecipes(): Flow<List<Recipe>> = recipeDAO.observeRecipes()

    override suspend fun insertRecipe(recipe: Recipe) = recipeDAO.saveRecipe(recipe)

    override suspend fun removeRecipe(recipe: Recipe) = recipeDAO.deleteRecipe(recipe)

    override fun loadSuggestedRecipes(prompt: String): Flow<List<Recipe>> = flow {
        val response = recipeService.getSuggestedRecipes(prompt, RecipeNumber.THREE.toName())

        val parts = response.candidates[0].content.parts
        val text = parts[0].text

        val geminiRecipes = parseResponseJson(text)
        val recipes = geminiRecipes.toRecipes()

        emit(recipes)
    }

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