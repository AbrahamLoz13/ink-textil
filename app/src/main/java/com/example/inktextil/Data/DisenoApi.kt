package com.example.inktextil.data

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface DisenoApi {
    @GET("posts")
    suspend fun obtenerDisenos(): List<Diseno>

    @POST("posts")
    suspend fun guardarDiseno(@Body diseno: Diseno): Diseno
}
