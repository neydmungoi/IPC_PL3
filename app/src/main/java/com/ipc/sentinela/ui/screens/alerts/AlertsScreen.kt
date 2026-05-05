package com.ipc.sentinela.ui.screens.alerts

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ipc.sentinela.data.model.Alerta
import com.ipc.sentinela.data.model.AlertaNivel
import com.ipc.sentinela.ui.screens.dashboard.DashboardViewModel
import com.ipc.sentinela.ui.theme.AlertLowGreen
import com.ipc.sentinela.ui.theme.AlertModerateOrange
import com.ipc.sentinela.ui.theme.AlertSevereRed
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun AlertsScreen(
    viewModel: DashboardViewModel = viewModel(),
    onAlertClick: (String) -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Alertas e Histórico",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Modo Soneca
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            colors = CardDefaults.cardColors(
                containerColor = if (uiState.isSnoozeActive) MaterialTheme.colorScheme.secondaryContainer 
                                 else MaterialTheme.colorScheme.surfaceVariant
            )
        ) {
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(text = "Modo Soneca", style = MaterialTheme.typography.titleMedium)
                    Text(
                        text = if (uiState.isSnoozeActive) "Alertas silenciados" else "Alertas ativos",
                        style = MaterialTheme.typography.bodySmall
                    )
                }
                Switch(
                    checked = uiState.isSnoozeActive,
                    onCheckedChange = { viewModel.toggleSnooze() }
                )
            }
        }

        // Lista de Alertas
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(bottom = 16.dp)
        ) {
            items(uiState.alertasRecentes) { alerta ->
                AlertaItem(
                    alerta = alerta,
                    onClick = { onAlertClick(alerta.id) }
                )
            }
        }
    }
}

@Composable
fun AlertaItem(alerta: Alerta, onClick: () -> Unit) {
    val indicatorColor = when (alerta.nivel) {
        AlertaNivel.BAIXO -> AlertLowGreen
        AlertaNivel.MODERADO -> AlertModerateOrange
        AlertaNivel.SEVERO -> AlertSevereRed
    }

    val sdf = SimpleDateFormat("HH:mm - dd MMM", Locale.getDefault())
    val dataFormatada = sdf.format(Date(alerta.timestamp))

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .height(IntrinsicSize.Min)
                .fillMaxWidth()
        ) {
            // Barra lateral de severidade
            Box(
                modifier = Modifier
                    .width(6.dp)
                    .fillMaxHeight()
                    .background(indicatorColor)
            )

            Column(
                modifier = Modifier
                    .padding(12.dp)
                    .weight(1f)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = alerta.tipo,
                        style = MaterialTheme.typography.labelLarge,
                        fontWeight = FontWeight.Bold,
                        color = indicatorColor
                    )
                    Text(
                        text = dataFormatada,
                        style = MaterialTheme.typography.labelSmall
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = alerta.recomendacao,
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            IconButton(
                onClick = { /* Lógica de áudio futuramente */ },
                modifier = Modifier.align(Alignment.CenterVertically)
            ) {
                Icon(
                    imageVector = Icons.Default.PlayArrow,
                    contentDescription = "Ouvir alerta",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}
