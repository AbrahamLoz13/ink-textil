package com.example.inktextil.data

class DisenoRepository {
    private val api = ApiClient.disenoApi

    suspend fun guardarDiseno(diseno: Diseno): Diseno {
        return api.guardarDiseno(diseno)
    }

    suspend fun obtenerDisenos(): List<Diseno> {
        return api.obtenerDisenos()
    }
}
