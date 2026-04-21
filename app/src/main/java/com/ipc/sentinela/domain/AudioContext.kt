package com.ipc.sentinela.domain

/**
 * Define o tipo de feedback auditivo a ser utilizado.
 * Spearcon: Speech-based earcons (fala acelerada para rapidez).
 * Earcon: Sons abstratos sintetizados para representar eventos ou estados.
 */
enum class AudioType {
    SPEARCON, EARCON
}

/**
 * Modelo para gerir o contexto de áudio em toda a aplicação.
 */
data class AudioContext(
    val type: AudioType,
    val priority: AudioPriority,
    val messageId: String,
    val fallbackText: String
)

enum class AudioPriority {
    LOW, MEDIUM, HIGH, IMMEDIATE
}
