package com.example.comparapp.ui.screens.resultados

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.comparapp.AppContainer
import com.example.comparapp.domain.repository.AhorroRepository
import com.example.comparapp.domain.repository.GeocodingRepository
import kotlinx.coroutines.launch
import kotlin.random.Random

private data class ConfigProveedor(
    val nombre: String,
    val subtipo: String,
    val tarifaBase: Int,
    val precioPorKm: Int,
    val color: Color,
    val inicial: String,
    val minutos: Int,
)

private val PROVEEDORES = listOf(
    ConfigProveedor("DiDi",         "Express",     800,  1200, Color(0xFFFF6B00), "D", minutos = 8),
    ConfigProveedor("Uber",         "UberX",       1000, 1400, Color(0xFF1A1A1A), "U", minutos = 5),
    ConfigProveedor("Cabify",       "Cabify Lite", 1200, 1600, Color(0xFF7C3AED), "C", minutos = 7),
)

class ResultadosViewModel(
    savedStateHandle: SavedStateHandle,
    private val repository: GeocodingRepository,
    private val ahorroRepository: AhorroRepository
) : ViewModel() {

    private val origen: String = savedStateHandle["origen"] ?: ""
    private val destino: String = savedStateHandle["destino"] ?: ""

    var estado by mutableStateOf(
        ResultadosUiState(origen = origen, destino = destino, isLoading = true)
    )
        private set

    init {
        calcularPrecios()
    }

    fun onSeleccionarClick(proveedor: ProveedorResultado) {
        val maxPrecio = estado.proveedores.maxOfOrNull { it.precio } ?: return
        val ahorro = maxPrecio - proveedor.precio
        val usuarioId = AppContainer.usuarioActual.value?.id ?: 0
        viewModelScope.launch {
            ahorroRepository.guardar(origen, destino, ahorro, usuarioId)
        }
    }

    companion object

    fun calcularPrecios() {
        viewModelScope.launch {
            estado = estado.copy(isLoading = true, error = null)

            val coordOrigen = repository.geocodear(origen)
            val coordDestino = repository.geocodear(destino)

            if (coordOrigen == null || coordDestino == null) {
                estado = estado.copy(
                    isLoading = false,
                    error = "No se pudieron encontrar las direcciones. Verificá que estén bien escritas."
                )
                return@launch
            }

            val km = repository.distanciaKm(
                coordOrigen.first, coordOrigen.second,
                coordDestino.first, coordDestino.second
            )

            if (km == null) {
                estado = estado.copy(
                    isLoading = false,
                    error = "No se pudo calcular la ruta entre las direcciones ingresadas."
                )
                return@launch
            }

            Log.d("ResultadosVM", "km calculado: $km")

            val proveedores = PROVEEDORES.map { config ->
                val demanda = Random.nextDouble(0.9, 1.5)
                val precio = ((config.tarifaBase + config.precioPorKm * km) * demanda).toInt()
                Log.d("ResultadosVM", "${config.nombre}: demanda=$demanda precio=$precio")
                ProveedorResultado(
                    nombre = config.nombre,
                    subtipo = config.subtipo,
                    precio = precio,
                    color = config.color,
                    minutos = config.minutos,
                    inicial = config.inicial
                )
            }.sortedBy { it.precio }
             .mapIndexed { i, p -> p.copy(esMejor = i == 0) }

            estado = estado.copy(
                isLoading = false,
                proveedores = proveedores,
                ahorro = proveedores.last().precio - proveedores.first().precio,
                distanciaKm = km,
                latOrigen = coordOrigen.first,
                lonOrigen = coordOrigen.second,
                latDestino = coordDestino.first,
                lonDestino = coordDestino.second
            )
        }
    }
}

fun ResultadosViewModel.Companion.factory(): ViewModelProvider.Factory = viewModelFactory {
    initializer {
        ResultadosViewModel(
            savedStateHandle = createSavedStateHandle(),
            repository = AppContainer.geoRepository,
            ahorroRepository = AppContainer.ahorroRepository
        )
    }
}

