package com.project.mindmate.Models

import com.google.firebase.Timestamp
import java.util.Date

data class MoodModel(
    val userMood : String,
    val timestamp : Timestamp
)
