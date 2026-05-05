package com.ipc.sentinela.ui.screens.dashboard

import androidx.lifecycle.ViewModel
import com.ipc.sentinela.data.model.Alerta
import com.ipc.sentinela.data.model.AlertaNivel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class DashboardUiState(
    val nivelSeca: AlertaNivel = AlertaNivel.BAIXO,
    val humidadeMedia: Float = 45f,
    val isSimpleLanguageMode: Boolean = false,
    val historicoHumidade: List<Float> = emptyList(),
    val alertasRecentes: List<Alerta> = emptyList(),
    val isSnoozeActive: Boolean = false
)

class DashboardViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(DashboardUiState())
    val uiState: StateFlow<DashboardUiState> = _uiState.asStateFlow()

    init {
        loadMockData()
    }

    private fun loadMockData() {
        _uiState.value = _uiState.value.copy(
            nivelSeca = AlertaNivel.MODERADO,
            humidadeMedia = 32f,
            historicoHumidade = List(30) { (20..60).random().toFloat() },
            alertasRecentes = listOf(
                Alerta("1", "SPI-1", AlertaNivel.SEVERO, "Rega de emergência necessária.", System.currentTimeMillis()),
                Alerta("2", "SPEI-3", AlertaNivel.MODERADO, "Monitorizar humidade do solo.", System.currentTimeMillis() - 86400000),
                Alerta("3", "SPI-1", AlertaNivel.BAIXO, "Condições normais.", System.currentTimeMillis() - 172800000)
            )
        )
    }

    fun toggleSimpleLanguage() {
        _uiState.value = _uiState.value.copy(isSimpleLanguageMode = !_uiState.value.isSimpleLanguageMode)
    }

    fun toggleSnooze() {
        _uiState.value = _uiState.value.copy(isSnoozeActive = !_uiState.value.isSnoozeActive)
    }

    fun getDisplayNivel(nivel: AlertaNivel): String {
        return if (_uiState.value.isSimpleLanguageMode) {
            when (nivel) {
                AlertaNivel.BAIXO -> "Sem risco"
                AlertaNivel.MODERADO -> "Atenção necessária"
                AlertaNivel.SEVERO -> "Perigo de seca"
            }
        } else {
            when (nivel) {
                AlertaNivel.BAIXO -> "SPI Normal"
                AlertaNivel.MODERADO -> "SPI Moderado"
                AlertaNivel.SEVERO -> "SPI Severo"
            }
        }
    }
}
