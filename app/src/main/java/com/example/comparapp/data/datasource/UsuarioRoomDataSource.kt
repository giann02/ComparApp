package com.example.comparapp.data.datasource

import com.example.comparapp.data.local.dao.UsuarioDao
import com.example.comparapp.data.local.entity.UsuarioEntity

class UsuarioRoomDataSource(private val dao: UsuarioDao) {

    suspend fun guardar(nombre: String, email: String, password: String) {
        dao.insertar(UsuarioEntity(nombre = nombre, email = email, password = password))
    }

    suspend fun buscarPorEmail(email: String): UsuarioEntity? = dao.buscarPorEmail(email)

    suspend fun existeEmail(email: String): Boolean = dao.contarPorEmail(email) > 0
}
