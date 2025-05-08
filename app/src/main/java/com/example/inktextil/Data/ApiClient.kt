package com.example.inktextil.data

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import kotlinx.serialization.ExperimentalSerializationApi
import okhttp3.MediaType.Companion.toMediaType

@OptIn(ExperimentalSerializationApi::class)
object ApiClient {
    private val json = Json { ignoreUnknownKeys = true }

    private val client = OkHttpClient.Builder().build()

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://jsonplaceholder.typicode.com/") // ✅ API de prueba
        .client(client)
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .build()

    val disenoApi: DisenoApi = retrofit.create(DisenoApi::class.java)
}
