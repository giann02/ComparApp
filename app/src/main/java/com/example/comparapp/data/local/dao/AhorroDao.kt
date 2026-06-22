package com.example.comparapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.comparapp.data.local.entity.AhorroEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AhorroDao {
    @Insert
    suspend fun insertar(ahorro: AhorroEntity)

    @Query("SELECT * FROM ahorros WHERE usuarioId = :usuarioId ORDER BY fecha DESC LIMIT 2")
    fun obtenerUltimos(usuarioId: Int): Flow<List<AhorroEntity>>

    @Query("SELECT * FROM ahorros WHERE usuarioId = :usuarioId ORDER BY fecha DESC")
    fun obtenerTodos(usuarioId: Int): Flow<List<AhorroEntity>>

    @Query("SELECT COALESCE(SUM(ahorro), 0) FROM ahorros WHERE usuarioId = :usuarioId")
    fun obtenerTotal(usuarioId: Int): Flow<Int>

    @Query("SELECT AVG(ahorro) FROM ahorros WHERE usuarioId = :usuarioId")
    fun obtenerPromedio(usuarioId: Int): Flow<Double?>
}
