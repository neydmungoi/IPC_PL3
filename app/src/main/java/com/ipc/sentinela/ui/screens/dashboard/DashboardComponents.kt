package com.ipc.sentinela.ui.screens.dashboard

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ipc.sentinela.data.model.AlertaNivel
import com.ipc.sentinela.ui.theme.AlertLowGreen
import com.ipc.sentinela.ui.theme.AlertModerateOrange
import com.ipc.sentinela.ui.theme.AlertSevereRed

import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics

@Composable
fun CartaoAlerta(nivel: AlertaNivel, titulo: String, descricao: String) {
    val backgroundColor = when (nivel) {
        AlertaNivel.BAIXO -> AlertLowGreen
        AlertaNivel.MODERADO -> AlertModerateOrange
        AlertaNivel.SEVERO -> AlertSevereRed
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .semantics { 
                contentDescription = "Alerta: $titulo. $descricao" 
            },
        colors = CardDefaults.cardColors(containerColor = backgroundColor),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Warning,
                contentDescription = null, // Texto já lido pelo Card
                tint = Color.White,
                modifier = Modifier.size(40.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(text = titulo, style = MaterialTheme.typography.titleLarge, color = Color.White)
                Text(text = descricao, style = MaterialTheme.typography.bodyMedium, color = Color.White)
            }
        }
    }
}

@Composable
fun MapaCalorHumidade(pontos: List<Float>) {
    val avgHumidade = if(pontos.isNotEmpty()) pontos.average().toInt() else 0
    Column(modifier = Modifier.semantics { 
        contentDescription = "Mapa de calor da humidade do solo. Humidade média de $avgHumidade por cento." 
    }) {
        Text("Distribuição de Humidade (Propriedade)", style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(8.dp))
        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
                .background(Color.LightGray.copy(alpha = 0.2f), RoundedCornerShape(8.dp))
        ) {
            val rows = 3
            val cols = 3
            val cellWidth = size.width / cols
            val cellHeight = size.height / rows

            for (i in 0 until rows) {
                for (j in 0 until cols) {
                    val index = i * cols + j
                    val humidade = if (index < pontos.size) pontos[index] else 50f
                    val color = Color(
                        red = (1f - humidade / 100f).coerceIn(0f, 1f),
                        green = (humidade / 200f).coerceIn(0f, 1f),
                        blue = (humidade / 100f).coerceIn(0f, 1f),
                        alpha = 0.7f
                    )
                    drawRect(
                        color = color,
                        topLeft = Offset(j * cellWidth, i * cellHeight),
                        size = androidx.compose.ui.geometry.Size(cellWidth, cellHeight)
                    )
                }
            }
        }
    }
}

@Composable
fun GraficoEvolucao(dados: List<Float>) {
    Column(modifier = Modifier.semantics { 
        contentDescription = "Gráfico de evolução da humidade nos últimos 30 dias." 
    }) {
        Text("Evolução 30 dias", style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(8.dp))
        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
                .padding(horizontal = 8.dp)
        ) {
            if (dados.isEmpty()) return@Canvas

            val spacing = size.width / (dados.size - 1)
            val maxVal = 100f
            val path = Path().apply {
                dados.forEachIndexed { index, valor ->
                    val x = index * spacing
                    val y = size.height - (valor / maxVal * size.height)
                    if (index == 0) moveTo(x, y) else lineTo(x, y)
                }
            }

            drawPath(
                path = path,
                color = AlertModerateOrange,
                style = Stroke(width = 3.dp.toPx())
            )

            dados.forEachIndexed { index, valor ->
                val x = index * spacing
                val y = size.height - (valor / maxVal * size.height)
                val color = when {
                    valor < 30 -> AlertSevereRed
                    valor < 60 -> AlertModerateOrange
                    else -> AlertLowGreen
                }
                drawCircle(color = color, radius = 4.dp.toPx(), center = Offset(x, y))
            }
        }
    }
}
