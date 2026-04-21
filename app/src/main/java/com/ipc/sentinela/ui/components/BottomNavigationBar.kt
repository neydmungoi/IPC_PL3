package com.ipc.sentinela.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState

sealed class Screen(val route: String, val icon: ImageVector, val label: String) {
    object Dashboard : Screen("dashboard", Icons.Default.Home, "Home")
    object Alerts : Screen("alerts", Icons.Default.Notifications, "Alertas")
    object Recommendations : Screen("recommendations", Icons.Default.PlayArrow, "Apoio")
    object Profile : Screen("profile", Icons.Default.Person, "Perfil")
}

@Composable
fun SentinelaBottomBar(navController: NavController) {
    val items = listOf(
        Screen.Dashboard,
        Screen.Alerts,
        Screen.Recommendations,
        Screen.Profile
    )
    
    val navBackStackEntry = navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry.value?.destination?.route

    NavigationBar(
        tonalElevation = 8.dp
    ) {
        items.forEach { screen ->
            val isSelected = currentRoute == screen.route
            NavigationBarItem(
                icon = {
                    Icon(screen.icon, contentDescription = screen.label)
                },
                label = { Text(screen.label) },
                selected = isSelected,
                onClick = {
                    if (currentRoute != screen.route) {
                        navController.navigate(screen.route) {
                            popUpTo(navController.graph.startDestinationId) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                }
            )
        }
    }
}
