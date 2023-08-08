package com.project.mindmate.Models

import com.google.firebase.Timestamp

data class MeditationModel(
    val durationMinutes: Int,
    val startTime: String,
    val endTime: String,
    val timestamp: Timestamp,
    val date: String
)

