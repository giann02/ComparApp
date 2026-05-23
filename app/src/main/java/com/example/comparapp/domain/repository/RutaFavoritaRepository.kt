package com.example.comparapp.domain.repository

import com.example.comparapp.domain.model.RutaFavorita
import kotlinx.coroutines.flow.Flow

interface RutaFavoritaRepository {
    fun obtenerFavoritas(usuarioId: Int): Flow<List<RutaFavorita>>
    suspend fun agregar(origen: String, destino: String, usuarioId: Int)
    suspend fun eliminar(ruta: RutaFavorita)
}
