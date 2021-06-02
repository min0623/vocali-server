package com.cs492e.vocali.model

data class RecommendationRequest(
    val prefWeight: Float = 0.5f,
    val moodWeight: Float = 0.5f,
    val pitchWeight: Float = 0.5f,
    val likeList: List<String> = emptyList(),
    val dislikeList: List<String> = emptyList(),
    val minPitch: String = "",
    val maxPitch: String = "",
    val moods: List<String> = emptyList()
)
