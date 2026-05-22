package com.example.comparapp.data.datasource

import com.example.comparapp.data.local.dao.RutaFavoritaDao
import com.example.comparapp.data.local.entity.RutaFavoritaEntity
import kotlinx.coroutines.flow.Flow

class RutaFavoritaDataSource(private val dao: RutaFavoritaDao) {
    fun obtenerPorUsuario(usuarioId: Int): Flow<List<RutaFavoritaEntity>> = dao.obtenerPorUsuario(usuarioId)
    suspend fun insertar(origen: String, destino: String, usuarioId: Int) =
        dao.insertar(RutaFavoritaEntity(usuarioId = usuarioId, origen = origen, destino = destino))
    suspend fun eliminar(id: Int) = dao.eliminar(id)
}
