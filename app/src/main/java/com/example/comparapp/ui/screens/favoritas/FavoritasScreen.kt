package com.example.comparapp.ui.screens.favoritas

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun FavoritasScreen(
    onBack: () -> Unit,
    onRutaClick: (origen: String, destino: String) -> Unit,
    viewModel: FavoritasViewModel = viewModel(factory = FavoritasViewModel.Factory)
) {
    FavoritasContent(
        estado = viewModel.estado,
        onBack = onBack,
        onRutaClick = onRutaClick,
        onEliminar = viewModel::eliminar
    )
}
