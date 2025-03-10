package com.noemi.aichef.model

import kotlinx.serialization.Serializable

@Serializable
data class GeminiRequest(
    val contents: List<GeminiRequestContent>
)

@Serializable
data class GeminiRequestContent(
    val parts: List<GeminiRequestPart>
)

@Serializable
data class GeminiRequestPart(
    val text: String
)