package com.project.mindmate.Models

import com.google.firebase.Timestamp

data class DailyLogsModel(
    var mood: String = "",
    var triggers: List<String> = emptyList(),
    var sleep_quality: String = "",
    var water_intake: Long = 0,
    var notes: String = "",
    var timestamp: Timestamp = Timestamp.now()
)
