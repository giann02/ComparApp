package com.example.comparapp.ui.screens.main

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.comparapp.AppContainer

@Composable
fun MainScreen(
    onCompararClick: (origen: String, destino: String) -> Unit = { _, _ -> },
    onVerTodas: () -> Unit = {},
    onCerrarSesion: () -> Unit = {},
    viewModel: MainViewModel = viewModel(
        factory = viewModelFactory {
            initializer {
                MainViewModel(
                    rutaFavoritaRepository = AppContainer.rutaFavoritaRepository,
                    geoRepository = AppContainer.geoRepository
                )
            }
        }
    )
) {
    MainContent(
        estado = viewModel.estado,
        onOrigenChange = viewModel::onOrigenChange,
        onDestinoChange = viewModel::onDestinoChange,
        onSugerenciaOrigenClick = viewModel::onSugerenciaOrigenClick,
        onSugerenciaDestinoClick = viewModel::onSugerenciaDestinoClick,
        onSwapClick = viewModel::onSwapClick,
        onCompararClick = { onCompararClick(viewModel.estado.origen, viewModel.estado.destino) },
        onTabSelected = viewModel::onTabSelected,
        onRutaFavoritaClick = viewModel::onRutaFavoritaClick,
        onEliminarFavorita = viewModel::eliminarFavorita,
        onGuardarFavorita = { viewModel.agregarFavorita(viewModel.estado.origen, viewModel.estado.destino) },

        onVerTodas = onVerTodas,
        onCerrarSesion = onCerrarSesion,
        modifier = Modifier.fillMaxSize()
    )
}
