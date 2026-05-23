package com.example.comparapp.ui.screens.resultados

import androidx.compose.ui.graphics.Color

data class ProveedorResultado(
    val nombre: String,
    val subtipo: String,
    val precio: Int,
    val color: Color,
    val inicial: String,
    val esMejor: Boolean = false
)

data class ResultadosUiState(
    val origen: String = "",
    val destino: String = "",
    val proveedores: List<ProveedorResultado> = emptyList(),
    val ahorro: Int = 0,
    val distanciaKm: Double = 0.0,
    val isLoading: Boolean = false,
    val error: String? = null
)
