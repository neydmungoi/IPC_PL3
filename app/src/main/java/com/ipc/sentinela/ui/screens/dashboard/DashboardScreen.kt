package com.ipc.sentinela.ui.screens.dashboard

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun DashboardScreen(viewModel: DashboardViewModel = viewModel()) {
    val uiState by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Estado da Região",
                style = MaterialTheme.typography.headlineSmall
            )
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("Linguagem Simples", style = MaterialTheme.typography.bodySmall)
                Switch(
                    checked = uiState.isSimpleLanguageMode,
                    onCheckedChange = { viewModel.toggleSimpleLanguage() }
                )
            }
        }

        CartaoAlerta(
            nivel = uiState.nivelSeca,
            titulo = viewModel.getDisplayNivel(uiState.nivelSeca),
            descricao = "Humidade média atual: ${uiState.humidadeMedia}%"
        )

        MapaCalorHumidade(pontos = uiState.historicoHumidade.take(9)) // Usando as primeiras 9 medidas para o grid 3x3

        GraficoEvolucao(dados = uiState.historicoHumidade)

        Spacer(modifier = Modifier.height(24.dp))
    }
}
