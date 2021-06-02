package com.cs492e.vocali.model

data class RecommendationResponse(
    val id: String,
    val title: String,
    val artist: String,
    val genre: String,
    val publishedYear: Int,
    val songNum: Int,
    val moodScore: Float,
    val prefScore: Float,
    val pitchScore: Float,
    val totalScore: Float
) {
    constructor(song: Song, model: ModelResponse) : this(
        song.id,
        song.title,
        song.artist,
        song.genre,
        song.publishedYear,
        song.songNum,
        model.mood_score,
        model.preference_score,
        model.pitch_score,
        model.total_score
    )
}