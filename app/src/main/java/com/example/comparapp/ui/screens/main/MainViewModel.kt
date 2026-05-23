package com.example.comparapp.ui.screens.main

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.comparapp.AppContainer
import com.example.comparapp.domain.model.RutaFavorita
import com.example.comparapp.domain.repository.GeocodingRepository
import com.example.comparapp.domain.repository.RutaFavoritaRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

private const val DEBOUNCE_MS = 10L
private const val MIN_CHARS = 4

class MainViewModel(
    private val rutaFavoritaRepository: RutaFavoritaRepository,
    private val geoRepository: GeocodingRepository
) : ViewModel() {

    var estado by mutableStateOf(
        MainUiState(
            nombreUsuario = AppContainer.usuarioActual?.nombre ?: "",
            emailUsuario  = AppContainer.usuarioActual?.email  ?: ""
        )
    )
        private set

    private var jobOrigen: Job? = null
    private var jobDestino: Job? = null

    private val usuarioId: Int get() = AppContainer.usuarioActual?.id ?: 0

    init {
        viewModelScope.launch {
            rutaFavoritaRepository.obtenerFavoritas(usuarioId).collect { favoritas ->
                estado = estado.copy(rutasFavoritas = favoritas)
            }
        }
    }

    fun onOrigenChange(origen: String) {
        estado = estado.copy(origen = origen, sugerenciasOrigen = emptyList())
        jobOrigen?.cancel()
        if (origen.length >= MIN_CHARS) {
            jobOrigen = viewModelScope.launch {
                delay(DEBOUNCE_MS)
                val sugerencias = geoRepository.autocompletar(origen)
                estado = estado.copy(sugerenciasOrigen = sugerencias)
            }
        }
    }

    fun onDestinoChange(destino: String) {
        estado = estado.copy(destino = destino, sugerenciasDestino = emptyList())
        jobDestino?.cancel()
        if (destino.length >= MIN_CHARS) {
            jobDestino = viewModelScope.launch {
                delay(DEBOUNCE_MS)
                val sugerencias = geoRepository.autocompletar(destino)
                estado = estado.copy(sugerenciasDestino = sugerencias)
            }
        }
    }

    fun onSugerenciaOrigenClick(sugerencia: String) {
        jobOrigen?.cancel()
        estado = estado.copy(origen = sugerencia, sugerenciasOrigen = emptyList())
    }

    fun onSugerenciaDestinoClick(sugerencia: String) {
        jobDestino?.cancel()
        estado = estado.copy(destino = sugerencia, sugerenciasDestino = emptyList())
    }

    fun onSwapClick() {
        jobOrigen?.cancel()
        jobDestino?.cancel()
        estado = estado.copy(
            origen = estado.destino,
            destino = estado.origen,
            sugerenciasOrigen = emptyList(),
            sugerenciasDestino = emptyList()
        )
    }

    fun onTabSelected(tab: Int) {
        estado = estado.copy(selectedTab = tab)
    }

    fun onRutaFavoritaClick(ruta: RutaFavorita) {
        estado = estado.copy(
            origen = ruta.origen,
            destino = ruta.destino,
            sugerenciasOrigen = emptyList(),
            sugerenciasDestino = emptyList()
        )
    }

    fun agregarFavorita(origen: String, destino: String): Boolean {
        val yaExiste = estado.rutasFavoritas.any {
            it.origen.trim().equals(origen.trim(), ignoreCase = true) &&
            it.destino.trim().equals(destino.trim(), ignoreCase = true)
        }
        if (yaExiste) return false
        viewModelScope.launch { rutaFavoritaRepository.agregar(origen, destino, usuarioId) }
        return true
    }

    fun eliminarFavorita(ruta: RutaFavorita) {
        viewModelScope.launch { rutaFavoritaRepository.eliminar(ruta) }
    }
}
