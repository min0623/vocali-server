package com.cs492e.vocali.model

data class ModelResponse(
    val title: String,
    val artist: String,
    val id: String,
    val mood_score: Float,
    val preference_score: Float,
    val pitch_score: Float,
    val total_score: Float
)
