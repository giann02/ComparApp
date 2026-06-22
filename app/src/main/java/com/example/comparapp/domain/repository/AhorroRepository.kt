package com.example.comparapp.domain.repository

import com.example.comparapp.domain.model.Ahorro
import kotlinx.coroutines.flow.Flow

interface AhorroRepository {
    fun obtenerUltimos(usuarioId: Int): Flow<List<Ahorro>>
    fun obtenerTodos(usuarioId: Int): Flow<List<Ahorro>>
    fun obtenerTotal(usuarioId: Int): Flow<Int>
    fun obtenerPromedio(usuarioId: Int): Flow<Double?>
    suspend fun guardar(origen: String, destino: String, ahorro: Int, usuarioId: Int)
}
