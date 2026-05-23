package com.example.comparapp.domain.repository

interface GeocodingRepository {
    suspend fun geocodear(direccion: String): Pair<Double, Double>?
    suspend fun distanciaKm(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Double?
    suspend fun autocompletar(texto: String): List<String>
}
