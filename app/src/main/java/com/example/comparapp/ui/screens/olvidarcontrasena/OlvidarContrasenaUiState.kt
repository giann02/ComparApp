package com.example.comparapp.ui.screens.olvidarcontrasena

data class OlvidarContrasenaUiState(
    val email: String = "",
    val nuevaPassword: String = "",
    val confirmarPassword: String = "",
    val isLoading: Boolean = false,
    val error: String? = null,
    val exito: Boolean = false
)
