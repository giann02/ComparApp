package com.example.comparapp.ui.screens.main

import com.example.comparapp.domain.model.RutaFavorita

data class MainUiState(
    val origen: String = "",
    val destino: String = "",
    val selectedTab: Int = 0,
    val rutasFavoritas: List<RutaFavorita> = emptyList(),
    val nombreUsuario: String = "",
    val emailUsuario: String = "",
    val sugerenciasOrigen: List<String> = emptyList(),
    val sugerenciasDestino: List<String> = emptyList()
)
