package com.ipc.sentinela.data.model

import java.util.Date

enum class AlertSeverity {
    INFO, LOW, MODERATE, HIGH, CRITICAL
}

data class Alert(
    val id: String,
    val title: String,
    val description: String,
    val timestamp: Date,
    val severity: AlertSeverity,
    val audioUrl: String? = null,
    val isRead: Boolean = false
)
