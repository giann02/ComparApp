package com.example.comparapp.data.repository

import com.example.comparapp.data.datasource.RutaFavoritaDataSource
import com.example.comparapp.data.local.entity.RutaFavoritaEntity
import com.example.comparapp.domain.model.RutaFavorita
import com.example.comparapp.domain.repository.RutaFavoritaRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RutaFavoritaRepositoryImpl(
    private val dataSource: RutaFavoritaDataSource
) : RutaFavoritaRepository {

    override fun obtenerFavoritas(usuarioId: Int): Flow<List<RutaFavorita>> =
        dataSource.obtenerPorUsuario(usuarioId).map { list -> list.map { it.toDomain() } }

    override suspend fun agregar(origen: String, destino: String, usuarioId: Int) {
        dataSource.insertar(origen, destino, usuarioId)
    }

    override suspend fun eliminar(ruta: RutaFavorita) {
        dataSource.eliminar(ruta.id)
    }
}

private fun RutaFavoritaEntity.toDomain() = RutaFavorita(id = id, origen = origen, destino = destino)
