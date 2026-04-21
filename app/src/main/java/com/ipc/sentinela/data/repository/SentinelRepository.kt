package com.ipc.sentinela.data.repository

import com.ipc.sentinela.data.model.Alert
import com.ipc.sentinela.data.model.AlertSeverity
import com.ipc.sentinela.data.model.DroughtMetrics
import com.ipc.sentinela.data.model.RiskLevel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.Date
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SentinelRepository @Inject constructor() {

    fun getDroughtMetrics(): Flow<DroughtMetrics> = flow {
        // Simulated data
        emit(
            DroughtMetrics(
                spi = -1.5f,
                spei = -1.2f,
                soilMoisture = 0.35f,
                reservoirLevel = 0.42f,
                riskLevel = RiskLevel.SEVERE
            )
        )
    }

    fun getAlerts(): Flow<List<Alert>> = flow {
        emit(
            listOf(
                Alert(
                    id = "1",
                    title = "Agravamento - SPI Severo",
                    description = "Tendência de seca severa detectada na região do Douro.",
                    timestamp = Date(),
                    severity = AlertSeverity.CRITICAL,
                    audioUrl = "mock_url_1"
                ),
                Alert(
                    id = "2",
                    title = "Recomendação de Rega",
                    description = "Considere ajustar o ciclo de rega para as 22h.",
                    timestamp = Date(),
                    severity = AlertSeverity.MODERATE
                )
            )
        )
    }
}
