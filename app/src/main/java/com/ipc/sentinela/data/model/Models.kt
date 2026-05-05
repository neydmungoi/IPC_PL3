package com.ipc.sentinela.data.model

import androidx.compose.ui.graphics.vector.ImageVector

data class Cultura(
    val id: String,
    val nome: String,
    val icone: ImageVector
)

enum class AlertaNivel {
    BAIXO, MODERADO, SEVERO
}

data class Alerta(
    val id: String,
    val tipo: String, // ex: SPI, SPEI
    val nivel: AlertaNivel,
    val recomendacao: String,
    val timestamp: Long
)

data class AudioPreferencias(
    val volume: Float = 0.5f,
    val spearconsEnabled: Boolean = true,
    val earconsEnabled: Boolean = true
)
