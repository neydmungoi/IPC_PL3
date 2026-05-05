package com.ipc.sentinela.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.ipc.sentinela.ui.screens.alerts.AlertsScreen
import com.ipc.sentinela.ui.screens.dashboard.DashboardScreen
import com.ipc.sentinela.ui.screens.library.LibraryScreen
import com.ipc.sentinela.ui.screens.onboarding.OnboardingScreen
import com.ipc.sentinela.ui.screens.profile.ProfileScreen

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Onboarding.route
    ) {
        composable(Screen.Onboarding.route) {
            OnboardingScreen(onConfigComplete = {
                navController.navigate(Screen.Dashboard.route) {
                    popUpTo(Screen.Onboarding.route) { inclusive = true }
                }
            })
        }
        composable(Screen.Dashboard.route) {
            DashboardScreen()
        }
        composable(Screen.Alertas.route) {
            AlertsScreen()
        }
        composable(Screen.Biblioteca.route) {
            LibraryScreen()
        }
        composable(Screen.Perfil.route) {
            ProfileScreen()
        }
    }
}
