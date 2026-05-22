package com.example.comparapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.comparapp.data.local.entity.RutaFavoritaEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RutaFavoritaDao {
    @Query("SELECT * FROM rutas_favoritas WHERE usuarioId = :usuarioId ORDER BY id ASC")
    fun obtenerPorUsuario(usuarioId: Int): Flow<List<RutaFavoritaEntity>>

    @Insert
    suspend fun insertar(ruta: RutaFavoritaEntity)

    @Query("DELETE FROM rutas_favoritas WHERE id = :id")
    suspend fun eliminar(id: Int)
}
