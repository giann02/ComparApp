package com.example.comparapp.ui.screens.resultados

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun ResultadosScreen(
    onBack: () -> Unit,
    viewModel: ResultadosViewModel = viewModel(factory = ResultadosViewModel.factory()),
    modifier: Modifier = Modifier
) {
    ResultadosContent(
        estado = viewModel.estado,
        onBack = onBack,
        onReintentar = viewModel::calcularPrecios,
        modifier = modifier
    )
}
