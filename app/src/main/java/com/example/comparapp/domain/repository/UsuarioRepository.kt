package com.example.comparapp.domain.repository

import com.example.comparapp.domain.model.Usuario

interface UsuarioRepository {
    suspend fun registrar(nombre: String, email: String, password: String): Result<Usuario>
    suspend fun login(email: String, password: String): Result<Usuario>
    suspend fun actualizarPassword(email: String, nuevaPassword: String): Result<Unit>
}
