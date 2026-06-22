package com.example.comparapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.comparapp.data.local.entity.UsuarioEntity

@Dao
interface UsuarioDao {
    @Insert
    suspend fun insertar(usuario: UsuarioEntity)

    @Query("SELECT * FROM usuarios WHERE LOWER(email) = LOWER(:email) LIMIT 1")
    suspend fun buscarPorEmail(email: String): UsuarioEntity?

    @Query("SELECT COUNT(*) FROM usuarios WHERE LOWER(email) = LOWER(:email)")
    suspend fun contarPorEmail(email: String): Int

    @Query("UPDATE usuarios SET password = :password WHERE LOWER(email) = LOWER(:email)")
    suspend fun actualizarPassword(email: String, password: String)
}
