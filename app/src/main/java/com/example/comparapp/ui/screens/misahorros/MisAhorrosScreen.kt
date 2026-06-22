package com.example.comparapp.ui.screens.misahorros

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.comparapp.AppContainer

@Composable
fun MisAhorrosScreen(
    onTabSelected: (Int) -> Unit,
    onVerHistorial: () -> Unit = {},
    viewModel: MisAhorrosViewModel = viewModel(
        factory = viewModelFactory {
            initializer {
                MisAhorrosViewModel(ahorroRepository = AppContainer.ahorroRepository)
            }
        }
    )
) {
    MisAhorrosContent(
        estado = viewModel.estado,
        onTabSelected = onTabSelected,
        onVerHistorial = onVerHistorial,
        modifier = Modifier.fillMaxSize()
    )
}
