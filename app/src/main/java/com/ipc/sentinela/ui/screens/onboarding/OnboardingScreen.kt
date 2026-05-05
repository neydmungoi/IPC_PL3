package com.ipc.sentinela.ui.screens.onboarding

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OnboardingScreen(onConfigComplete: () -> Unit) {
    var localizacao by remember { mutableStateOf("") }
    var isLoadingGps by remember { mutableStateOf(false) }
    var gpsError by remember { mutableStateOf<String?>(null) }
    val culturas = listOf("Vinha", "Olival", "Milho")
    val selecionadas = remember { mutableStateListOf<String>() }
    var spearconsEnabled by remember { mutableStateOf(true) }
    var earconsEnabled by remember { mutableStateOf(true) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Text(
            text = "Configuração Inicial",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.primary
        )

        // Localização com Estado de Carregamento e Erro
        Column {
            OutlinedTextField(
                value = localizacao,
                onValueChange = { 
                    localizacao = it 
                    gpsError = null
                },
                label = { Text("Localização da Propriedade") },
                modifier = Modifier.fillMaxWidth(),
                isError = gpsError != null,
                trailingIcon = {
                    if (isLoadingGps) {
                        CircularProgressIndicator(modifier = Modifier.size(24.dp))
                    } else {
                        IconButton(onClick = { 
                            isLoadingGps = true
                            // Simulação de delay de GPS
                            android.os.Handler(android.os.Looper.getMainLooper()).postDelayed({
                                isLoadingGps = false
                                gpsError = "Erro: Sinal de GPS fraco no Douro."
                            }, 1500)
                        }) {
                            Icon(
                                imageVector = Icons.Default.LocationOn,
                                contentDescription = "Obter localização via GPS"
                            )
                        }
                    }
                }
            )
            if (gpsError != null) {
                Text(
                    text = gpsError!!,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(start = 16.dp, top = 4.dp)
                )
            }
        }

        // Seleção de Culturas
        Column(modifier = Modifier.fillMaxWidth()) {
            Text("Culturas a Monitorizar", style = MaterialTheme.typography.titleMedium)
            culturas.forEach { cultura ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Checkbox(
                        checked = selecionadas.contains(cultura),
                        onCheckedChange = {
                            if (it) selecionadas.add(cultura) else selecionadas.remove(cultura)
                        }
                    )
                    Text(text = cultura)
                }
            }
        }

        // Definições de Áudio
        Column(modifier = Modifier.fillMaxWidth()) {
            Text("Definições de Áudio", style = MaterialTheme.typography.titleMedium)
            
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Voz (Spearcons)")
                Switch(
                    checked = spearconsEnabled,
                    onCheckedChange = { spearconsEnabled = it },
                    modifier = Modifier.semantics { contentDescription = "Ativar Spearcons" }
                )
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Sons (Earcons)")
                Switch(
                    checked = earconsEnabled,
                    onCheckedChange = { earconsEnabled = it },
                    modifier = Modifier.semantics { contentDescription = "Ativar Earcons" }
                )
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = onConfigComplete,
            modifier = Modifier.fillMaxWidth(),
            shape = MaterialTheme.shapes.medium
        ) {
            Text("Concluir Configuração")
        }
    }
}
