package com.cs492e.vocali.model

data class UserRequest(
    val name: String?,
    val age: Int?,
    val minPitch: String?,
    val maxPitch: String?,
    val pitchWeight: Double?,
    val moodWeight: Double?,
    val prefWeight: Double?
    )