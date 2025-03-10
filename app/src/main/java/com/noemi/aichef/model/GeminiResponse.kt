package com.noemi.aichef.model

import kotlinx.serialization.Serializable

@Serializable
data class GeminiResponse(
    val candidates: List<GeminiResponseCandidates>
)

@Serializable
data class GeminiResponseCandidates(
    val content: GeminiResponseContent
)

@Serializable
data class GeminiResponseContent(
    val parts: List<GeminiResponsePart>
)

@Serializable
data class GeminiResponsePart(
    val text: String
)