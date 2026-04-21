package com.ipc.sentinela.data.model

data class DroughtMetrics(
    val spi: Float,
    val spei: Float,
    val soilMoisture: Float, // Percentage 0.0 to 1.0
    val reservoirLevel: Float, // Percentage 0.0 to 1.0
    val riskLevel: RiskLevel
)

enum class RiskLevel {
    NORMAL, MODERATE, SEVERE, CRITICAL
}
