package com.example.comparapp.ui.screens.misahorros

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.comparapp.AppContainer
import com.example.comparapp.domain.repository.AhorroRepository
import kotlinx.coroutines.launch

class MisAhorrosViewModel(
    private val ahorroRepository: AhorroRepository
) : ViewModel() {

    private val usuarioId: Int get() = AppContainer.usuarioActual.value?.id ?: 0

    var estado by mutableStateOf(MisAhorrosUiState())
        private set

    init {
        viewModelScope.launch {
            ahorroRepository.obtenerTotal(usuarioId).collect { total ->
                estado = estado.copy(ahorroTotal = total)
            }
        }
        viewModelScope.launch {
            ahorroRepository.obtenerUltimos(usuarioId).collect { viajes ->
                estado = estado.copy(ultimosViajes = viajes)
            }
        }
        viewModelScope.launch {
            ahorroRepository.obtenerPromedio(usuarioId).collect { promedio ->
                estado = estado.copy(ahorroPromedio = promedio ?: 0.0)
            }
        }
    }
}
