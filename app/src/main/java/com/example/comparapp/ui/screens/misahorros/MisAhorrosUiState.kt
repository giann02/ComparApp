package com.example.comparapp.ui.screens.misahorros

import com.example.comparapp.domain.model.Ahorro

data class MisAhorrosUiState(
    val ahorroTotal: Int = 0,
    val ahorroPromedio: Double = 0.0,
    val ultimosViajes: List<Ahorro> = emptyList()
)
