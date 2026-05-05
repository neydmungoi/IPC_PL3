package com.ipc.sentinela

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ipc.sentinela.audio.AudioEngine
import com.ipc.sentinela.data.model.AlertaNivel
import com.ipc.sentinela.ui.screens.MainScreen
import com.ipc.sentinela.ui.screens.dashboard.DashboardViewModel
import com.ipc.sentinela.ui.theme.VoxNotiTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            VoxNotiTheme {
                val context = LocalContext.current
                val audioEngine = remember { AudioEngine(context) }
                val dashboardViewModel: DashboardViewModel = viewModel()

                // Listener global para eventos de áudio críticos
                LaunchedEffect(Unit) {
                    dashboardViewModel.audioEvents.collect { nivel ->
                        if (nivel == AlertaNivel.SEVERO) {
                            // No mundo real, aqui tocaríamos um Spearcon carregado
                            // Como não temos os ficheiros raw, vamos disparar a vibração hática
                            audioEngine.triggerCriticalHaptic()
                            // E poderiamos logar ou mostrar um Toast para debug
                        }
                    }
                }

                MainScreen()
            }
        }
    }
}
