package com.example.comparapp

import android.content.Context
import com.example.comparapp.data.datasource.GeocodingDataSource
import com.example.comparapp.data.datasource.RutaFavoritaDataSource
import com.example.comparapp.data.datasource.UsuarioRoomDataSource
import com.example.comparapp.data.local.ComparAppDatabase
import com.example.comparapp.data.repository.GeocodingRepositoryImpl
import com.example.comparapp.data.repository.RutaFavoritaRepositoryImpl
import com.example.comparapp.data.repository.UsuarioRepositoryImpl
import com.example.comparapp.domain.model.Usuario
import com.example.comparapp.domain.repository.GeocodingRepository
import com.example.comparapp.domain.repository.RutaFavoritaRepository
import com.example.comparapp.domain.repository.UsuarioRepository

object AppContainer {

    private lateinit var db: ComparAppDatabase

    var usuarioActual: Usuario? = null

    fun init(context: Context) {
        db = ComparAppDatabase.create(context)
    }

    fun cerrarSesion() {
        usuarioActual = null
    }

    val usuarioRepository: UsuarioRepository by lazy {
        UsuarioRepositoryImpl(UsuarioRoomDataSource(db.usuarioDao()))
    }

    val rutaFavoritaRepository: RutaFavoritaRepository by lazy {
        RutaFavoritaRepositoryImpl(RutaFavoritaDataSource(db.rutaFavoritaDao()))
    }

    val geoRepository: GeocodingRepository = GeocodingRepositoryImpl(GeocodingDataSource)
}
