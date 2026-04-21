package com.ipc.sentinela

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.ipc.sentinela.ui.components.SentinelaBottomBar
import com.ipc.sentinela.ui.screens.*
import com.ipc.sentinela.ui.theme.SentinelaTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SentinelaTheme {
                val navController = rememberNavController()
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route
                
                // Only show bottom bar if we are not on the onboarding screen
                val showBottomBar = currentRoute != "onboarding"

                Scaffold(
                    bottomBar = {
                        if (showBottomBar) {
                            SentinelaBottomBar(navController)
                        }
                    }
                ) { innerPadding ->
                    NavHost(
                        navController = navController, 
                        startDestination = "onboarding",
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable("onboarding") {
                            OnboardingScreen(onFinish = {
                                navController.navigate("dashboard") {
                                    popUpTo("onboarding") { inclusive = true }
                                }
                            })
                        }
                        composable("dashboard") {
                            DashboardScreen()
                        }
                        composable("alerts") {
                            AlertsScreen()
                        }
                        composable("recommendations") {
                            RecommendationsScreen()
                        }
                        composable("profile") {
                            ProfileScreen()
                        }
                    }
                }
            }
        }
    }
}
