package com.example.comparapp.data.remote

import com.google.gson.annotations.SerializedName
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MapboxApi {

    @GET("geocoding/v5/mapbox.places/{query}.json")
    suspend fun buscar(
        @Path("query") query: String,
        @Query("access_token") accessToken: String,
        @Query("language") language: String = "es",
        @Query("country") country: String = "AR",
        @Query("proximity") proximity: String = "-58.3816,-34.6037",
        @Query("types") types: String = "address,poi",
        @Query("limit") limit: Int = 5
    ): MapboxGeocodingResponse

    @GET("directions/v5/mapbox/driving/{coordinates}")
    suspend fun calcularRuta(
        @Path(value = "coordinates", encoded = true) coordinates: String,
        @Query("access_token") accessToken: String
    ): MapboxDirectionsResponse
}

data class MapboxGeocodingResponse(val features: List<MapboxFeature>)

data class MapboxFeature(
    @SerializedName("place_name") val placeName: String,
    val center: List<Double>  // [lon, lat]
)

data class MapboxDirectionsResponse(val routes: List<MapboxRoute>)

data class MapboxRoute(val distance: Double)  // en metros
