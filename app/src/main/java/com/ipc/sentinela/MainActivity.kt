package com.ipc.sentinela

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.ipc.sentinela.ui.screens.MainScreen
import com.ipc.sentinela.ui.theme.VoxNotiTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            VoxNotiTheme {
                MainScreen()
            }
        }
    }
}
