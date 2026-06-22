package com.example.comparapp.data.datasource

import com.example.comparapp.data.local.dao.AhorroDao
import com.example.comparapp.data.local.entity.AhorroEntity
import kotlinx.coroutines.flow.Flow

class AhorroDataSource(private val dao: AhorroDao) {
    fun obtenerUltimos(usuarioId: Int): Flow<List<AhorroEntity>> = dao.obtenerUltimos(usuarioId)
    fun obtenerTodos(usuarioId: Int): Flow<List<AhorroEntity>> = dao.obtenerTodos(usuarioId)
    fun obtenerTotal(usuarioId: Int): Flow<Int> = dao.obtenerTotal(usuarioId)
    fun obtenerPromedio(usuarioId: Int): Flow<Double?> = dao.obtenerPromedio(usuarioId)
    suspend fun insertar(origen: String, destino: String, ahorro: Int, usuarioId: Int) =
        dao.insertar(AhorroEntity(usuarioId = usuarioId, origen = origen, destino = destino, ahorro = ahorro))
}
