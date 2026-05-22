package com.example.comparapp.data.repository

import com.example.comparapp.data.datasource.GeocodingDataSource
import com.example.comparapp.domain.repository.GeocodingRepository

class GeocodingRepositoryImpl(
    private val dataSource: GeocodingDataSource
) : GeocodingRepository {

    override suspend fun geocodear(direccion: String): Pair<Double, Double>? {
        return dataSource.geocodear(direccion)
    }

    override suspend fun distanciaKm(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Double? {
        return dataSource.distanciaKm(lat1, lon1, lat2, lon2)
    }

    override suspend fun autocompletar(texto: String): List<String> {
        return dataSource.autocompletar(texto)
    }
}
