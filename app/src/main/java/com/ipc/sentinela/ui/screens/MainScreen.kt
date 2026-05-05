package com.ipc.sentinela.ui.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.ipc.sentinela.ui.navigation.Screen
import com.ipc.sentinela.ui.navigation.bottomNavItems
import com.ipc.sentinela.ui.screens.alerts.AlertDetailScreen
import com.ipc.sentinela.ui.screens.alerts.AlertsScreen
import com.ipc.sentinela.ui.screens.dashboard.DashboardScreen
import com.ipc.sentinela.ui.screens.library.LibraryScreen
import com.ipc.sentinela.ui.screens.onboarding.OnboardingScreen
import com.ipc.sentinela.ui.screens.profile.ProfileScreen

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    // Check if we should show the bottom bar (hide on onboarding)
    val showBottomBar = bottomNavItems.any { it.route == currentDestination?.route }

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                NavigationBar {
                    bottomNavItems.forEach { screen ->
                        NavigationBarItem(
                            icon = { screen.icon?.let { Icon(it, contentDescription = null) } },
                            label = { Text(screen.title) },
                            selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                            onClick = {
                                navController.navigate(screen.route) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Onboarding.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Onboarding.route) {
                OnboardingScreen(onConfigComplete = {
                    navController.navigate(Screen.Dashboard.route) {
                        popUpTo(Screen.Onboarding.route) { inclusive = true }
                    }
                })
            }
            composable(Screen.Dashboard.route) { DashboardScreen() }
            composable(Screen.Alertas.route) { 
                AlertsScreen(
                    onAlertClick = { alertId ->
                        navController.navigate(Screen.AlertaDetalhes.createRoute(alertId))
                    }
                ) 
            }
            composable(Screen.Biblioteca.route) { LibraryScreen() }
            composable(Screen.Perfil.route) { ProfileScreen() }
            composable(
                route = Screen.AlertaDetalhes.route,
                arguments = listOf(navArgument("alertId") { type = NavType.StringType })
            ) { backStackEntry ->
                AlertDetailScreen(
                    alertId = backStackEntry.arguments?.getString("alertId"),
                    onBack = { navController.popBackStack() }
                )
            }
        }
    }
}
