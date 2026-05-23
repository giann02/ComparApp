package com.example.comparapp.ui.screens.favoritas

import com.example.comparapp.domain.model.RutaFavorita

data class FavoritasUiState(
    val favoritas: List<RutaFavorita> = emptyList()
)
