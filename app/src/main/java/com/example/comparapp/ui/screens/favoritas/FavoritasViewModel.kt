package com.example.comparapp.ui.screens.favoritas

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.comparapp.AppContainer
import com.example.comparapp.domain.model.RutaFavorita
import com.example.comparapp.domain.repository.RutaFavoritaRepository
import kotlinx.coroutines.launch

class FavoritasViewModel(
    private val repository: RutaFavoritaRepository
) : ViewModel() {

    var estado by mutableStateOf(FavoritasUiState())
        private set

    init {
        val usuarioId = AppContainer.usuarioActual.value?.id ?: 0
        viewModelScope.launch {
            repository.obtenerFavoritas(usuarioId).collect { favoritas ->
                estado = estado.copy(favoritas = favoritas)
            }
        }
    }

    fun eliminar(ruta: RutaFavorita) {
        viewModelScope.launch { repository.eliminar(ruta) }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer { FavoritasViewModel(AppContainer.rutaFavoritaRepository) }
        }
    }
}
