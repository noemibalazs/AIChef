package com.noemi.aichef.service

import com.noemi.aichef.BuildConfig.GEMINI_KEY
import com.noemi.aichef.model.GeminiRequest
import com.noemi.aichef.model.GeminiRequestContent
import com.noemi.aichef.model.GeminiResponse
import com.noemi.aichef.model.GeminiRequestPart
import com.noemi.aichef.provider.DispatcherProvider
import com.noemi.aichef.util.Constants.BODY_TEXT
import com.noemi.aichef.util.Constants.KEY_PARAMETER
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.headers
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.HttpHeaders
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RecipeServiceImpl @Inject constructor(
    private val httpClient: HttpClient,
    private val dispatcherProvider: DispatcherProvider
) : RecipeService {

    override suspend fun getSuggestedRecipes(prompt: String, requestNumber: String): GeminiResponse = withContext(dispatcherProvider.io()) {
        val textPrompt = "Generate $requestNumber recipes for a $prompt dish. $BODY_TEXT."
        val request = GeminiRequest(
            contents = listOf(GeminiRequestContent(parts = listOf(GeminiRequestPart(text = textPrompt))))
        )

        val recipes = httpClient.post("https://generativelanguage.googleapis.com/v1beta/models/gemini-2.0-flash:generateContent") {
            headers {
                append(HttpHeaders.ContentType, "application/json")
                append(HttpHeaders.Connection, "keep-alive")
            }
            parameter(KEY_PARAMETER, GEMINI_KEY)
            setBody(request)
        }

        recipes.body()
    }
}