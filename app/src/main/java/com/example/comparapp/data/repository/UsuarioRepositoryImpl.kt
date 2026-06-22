package com.example.comparapp.data.repository

import com.example.comparapp.data.datasource.UsuarioRoomDataSource
import com.example.comparapp.data.datasource.hashPassword
import com.example.comparapp.data.local.entity.UsuarioEntity
import com.example.comparapp.domain.model.Usuario
import com.example.comparapp.domain.repository.UsuarioRepository

class UsuarioRepositoryImpl(
    private val dataSource: UsuarioRoomDataSource
) : UsuarioRepository {

    override suspend fun registrar(nombre: String, email: String, password: String): Result<Usuario> {
        if (dataSource.existeEmail(email)) {
            return Result.failure(Exception("El correo ya está registrado"))
        }
        dataSource.guardar(nombre, email, password)
        val entity = dataSource.buscarPorEmail(email)
            ?: return Result.failure(Exception("Error al crear el usuario"))
        return Result.success(entity.toDomain())
    }

    override suspend fun login(email: String, password: String): Result<Usuario> {
        val entity = dataSource.buscarPorEmail(email)
            ?: return Result.failure(Exception("Correo no registrado"))
        if (entity.password != hashPassword(password)) {
            return Result.failure(Exception("Contraseña incorrecta"))
        }
        return Result.success(entity.toDomain())
    }

    override suspend fun actualizarPassword(email: String, nuevaPassword: String): Result<Unit> {
        if (!dataSource.existeEmail(email)) {
            return Result.failure(Exception("El correo no está registrado"))
        }
        dataSource.actualizarPassword(email, nuevaPassword)
        return Result.success(Unit)
    }
}

private fun UsuarioEntity.toDomain() = Usuario(id = id, nombre = nombre, email = email)
