package com.example.comparapp.data.datasource

import android.util.Log
import com.example.comparapp.data.remote.MapboxApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object GeocodingDataSource {

    private const val ACCESS_TOKEN = "pk.eyJ1IjoiZ2lhbm4wMiIsImEiOiJjbW5mMGRoOGowNTh2Mm5wcHMyanVtenl0In0.mnb1yi5TmJZJrDxIwBzQxA"

    private val api: MapboxApi = Retrofit.Builder()
        .baseUrl("https://api.mapbox.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(MapboxApi::class.java)

    // Retorna (lat, lon) o null si no se encontró la dirección
    suspend fun geocodear(direccion: String): Pair<Double, Double>? {
        return try {
            val respuesta = api.buscar(query = direccion, accessToken = ACCESS_TOKEN, limit = 1)
            val feature = respuesta.features.firstOrNull() ?: return null
            Pair(feature.center[1], feature.center[0])  // center es [lon, lat]
        } catch (e: Exception) {
            Log.e("GeocodingDataSource", "geocodear('$direccion') falló: ${e::class.simpleName}: ${e.message}", e)
            null
        }
    }

    // Retorna la distancia en km por calles o null si falla
    suspend fun distanciaKm(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Double? {
        return try {
            val coordinates = "$lon1,$lat1;$lon2,$lat2"  // Mapbox usa lon,lat
            val respuesta = api.calcularRuta(coordinates = coordinates, accessToken = ACCESS_TOKEN)
            val metros = respuesta.routes.firstOrNull()?.distance ?: return null
            metros / 1000.0
        } catch (e: Exception) {
            Log.e("GeocodingDataSource", "distanciaKm($lat1,$lon1 → $lat2,$lon2) falló: ${e::class.simpleName}: ${e.message}", e)
            null
        }
    }

    // Retorna sugerencias de direcciones para el texto ingresado
    suspend fun autocompletar(texto: String): List<String> {
        return try {
            val respuesta = api.buscar(query = texto, accessToken = ACCESS_TOKEN)
            respuesta.features.map { it.placeName }.distinct()
        } catch (e: Exception) {
            Log.e("GeocodingDataSource", "autocompletar('$texto') falló: ${e::class.simpleName}: ${e.message}", e)
            emptyList()
        }
    }
}
