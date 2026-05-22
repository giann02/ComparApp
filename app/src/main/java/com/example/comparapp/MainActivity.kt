package com.example.comparapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.comparapp.ui.navigation.AppNavigation
import com.example.comparapp.ui.theme.ComparAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ComparAppTheme {
                AppNavigation()
            }
        }
    }
}
