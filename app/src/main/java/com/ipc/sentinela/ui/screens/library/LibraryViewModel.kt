package com.ipc.sentinela.ui.screens.library

import androidx.lifecycle.ViewModel
import com.ipc.sentinela.data.model.Podcast
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class LibraryUiState(
    val podcasts: List<Podcast> = emptyList(),
    val filter: String = "Geral",
    val currentPlaying: Podcast? = null,
    val isPlaying: Boolean = false,
    val progress: Float = 0f
)

class LibraryViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(LibraryUiState())
    val uiState: StateFlow<LibraryUiState> = _uiState.asStateFlow()

    private val allPodcasts = listOf(
        Podcast("1", "Boas Práticas de Rega", "Eng. Agrónomo", "12:30", "rega_vinha", "Vinha"),
        Podcast("2", "Gestão do Olival em Seca", "Dr. Oliveira", "08:45", "olival_seca", "Olival"),
        Podcast("3", "Fertilização Sustentável", "Maria Silva", "15:20", "fertilizacao", "Geral"),
        Podcast("4", "Novas Tecnologias no Douro", "João Porto", "10:15", "tech_douro", "Vinha")
    )

    init {
        _uiState.value = _uiState.value.copy(podcasts = allPodcasts)
    }

    fun setFilter(filter: String) {
        val filteredList = if (filter == "Geral") {
            allPodcasts
        } else {
            allPodcasts.filter { it.culturaRelacionada == filter }
        }
        _uiState.value = _uiState.value.copy(filter = filter, podcasts = filteredList)
    }

    fun playPodcast(podcast: Podcast) {
        _uiState.value = _uiState.value.copy(currentPlaying = podcast, isPlaying = true)
        // Lógica real do ExoPlayer seria chamada aqui
    }

    fun togglePlayback() {
        _uiState.value = _uiState.value.copy(isPlaying = !_uiState.value.isPlaying)
    }

    fun stopPlayback() {
        _uiState.value = _uiState.value.copy(currentPlaying = null, isPlaying = false)
    }
}
