package com.ipc.sentinela.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(val route: String, val title: String, val icon: ImageVector? = null) {
    object Onboarding : Screen("onboarding", "Configuração")
    object Dashboard : Screen("dashboard", "Dashboard", Icons.Default.Home)
    object Alertas : Screen("alerts", "Alertas", Icons.Default.DateRange)
    object Biblioteca : Screen("library", "Biblioteca", Icons.Default.List)
    object Perfil : Screen("profile", "Perfil", Icons.Default.Person)
    object AlertaDetalhes : Screen("alert_details/{alertId}", "Detalhes") {
        fun createRoute(alertId: String) = "alert_details/$alertId"
    }
}

val bottomNavItems = listOf(
    Screen.Dashboard,
    Screen.Alertas,
    Screen.Biblioteca,
    Screen.Perfil
)
