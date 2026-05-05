package com.ipc.sentinela.ui.screens.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ipc.sentinela.data.model.Alerta
import com.ipc.sentinela.data.model.AlertaNivel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.random.Random

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

    private val _audioEvents = MutableSharedFlow<AlertaNivel>()
    val audioEvents: SharedFlow<AlertaNivel> = _audioEvents.asSharedFlow()

    init {
        loadMockData()
        startDataSimulation()
    }

    private fun loadMockData() {
        val initialHistory = List(30) { Random.nextFloat() * 40 + 20 }
        _uiState.value = _uiState.value.copy(
            nivelSeca = AlertaNivel.MODERADO,
            humidadeMedia = initialHistory.last(),
            historicoHumidade = initialHistory,
            alertasRecentes = listOf(
                Alerta("1", "SPI-1", AlertaNivel.SEVERO, "Rega de emergência necessária.", System.currentTimeMillis()),
                Alerta("2", "SPEI-3", AlertaNivel.MODERADO, "Monitorizar humidade do solo.", System.currentTimeMillis() - 86400000),
                Alerta("3", "SPI-1", AlertaNivel.BAIXO, "Condições normais.", System.currentTimeMillis() - 172800000)
            )
        )
    }

    private fun startDataSimulation() {
        viewModelScope.launch {
            while (true) {
                delay(5000) // Atualiza a cada 5 segundos
                val currentHistory = _uiState.value.historicoHumidade.toMutableList()
                val newValue = (currentHistory.last() + Random.nextFloat() * 4 - 2).coerceIn(10f, 90f)
                
                currentHistory.removeAt(0)
                currentHistory.add(newValue)

                val novoNivel = when {
                    newValue < 25 -> AlertaNivel.SEVERO
                    newValue < 45 -> AlertaNivel.MODERADO
                    else -> AlertaNivel.BAIXO
                }

                // Disparar evento de áudio se entrar em nível crítico e não estiver em modo soneca
                if (novoNivel == AlertaNivel.SEVERO && _uiState.value.nivelSeca != AlertaNivel.SEVERO && !_uiState.value.isSnoozeActive) {
                    viewModelScope.launch {
                        _audioEvents.emit(AlertaNivel.SEVERO)
                    }
                }

                _uiState.value = _uiState.value.copy(
                    humidadeMedia = newValue.toInt().toFloat(),
                    historicoHumidade = currentHistory,
                    nivelSeca = novoNivel
                )
            }
        }
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
