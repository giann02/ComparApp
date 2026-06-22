package com.example.comparapp.data.repository

import com.example.comparapp.data.datasource.AhorroDataSource
import com.example.comparapp.data.local.entity.AhorroEntity
import com.example.comparapp.domain.model.Ahorro
import com.example.comparapp.domain.repository.AhorroRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class AhorroRepositoryImpl(
    private val dataSource: AhorroDataSource
) : AhorroRepository {

    override fun obtenerUltimos(usuarioId: Int): Flow<List<Ahorro>> =
        dataSource.obtenerUltimos(usuarioId).map { list -> list.map { it.toDomain() } }

    override fun obtenerTodos(usuarioId: Int): Flow<List<Ahorro>> =
        dataSource.obtenerTodos(usuarioId).map { list -> list.map { it.toDomain() } }

    override fun obtenerTotal(usuarioId: Int): Flow<Int> = dataSource.obtenerTotal(usuarioId)

    override fun obtenerPromedio(usuarioId: Int): Flow<Double?> = dataSource.obtenerPromedio(usuarioId)

    override suspend fun guardar(origen: String, destino: String, ahorro: Int, usuarioId: Int) {
        dataSource.insertar(origen, destino, ahorro, usuarioId)
    }
}

private fun AhorroEntity.toDomain() = Ahorro(
    id = id,
    origen = origen,
    destino = destino,
    ahorro = ahorro,
    fecha = fecha
)
