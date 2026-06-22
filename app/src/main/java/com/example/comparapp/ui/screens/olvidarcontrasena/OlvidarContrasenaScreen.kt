package com.example.comparapp.ui.screens.olvidarcontrasena

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.Composable
import com.example.comparapp.AppContainer

@Composable
fun OlvidarContrasenaScreen(
    onExito: () -> Unit,
    onBack: () -> Unit,
    viewModel: OlvidarContrasenaViewModel = viewModel(
        factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T =
                OlvidarContrasenaViewModel(AppContainer.usuarioRepository) as T
        }
    )
) {
    val estado = viewModel.estado
    OlvidarContrasenaContent(
        estado = estado,
        onEmailChange = viewModel::onEmailChange,
        onNuevaPasswordChange = viewModel::onNuevaPasswordChange,
        onConfirmarPasswordChange = viewModel::onConfirmarPasswordChange,
        onActualizar = { viewModel.onActualizar(onExito) },
        onBack = onBack
    )
}
