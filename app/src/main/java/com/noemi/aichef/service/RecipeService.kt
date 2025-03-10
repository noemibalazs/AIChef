package com.noemi.aichef.service

import com.noemi.aichef.model.GeminiResponse

interface RecipeService {

    suspend fun getSuggestedRecipes(prompt: String, requestNumber: String): GeminiResponse
}