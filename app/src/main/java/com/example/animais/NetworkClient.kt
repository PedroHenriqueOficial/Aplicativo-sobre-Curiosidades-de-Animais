package com.example.animais

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// Objeto Singleton para gerenciar os clientes de rede

object NetworkClient {
    private const val CAT_API_BASE_URL = "https://catfact.ninja/"
    private const val DOG_API_BASE_URL = "https://dog-api.kinduff.com/"

    // Função genérica para criar um cliente Retrofit
    private fun getClient(baseUrl: String): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl) // Define o URL base
            .addConverterFactory(GsonConverterFactory.create()) // Adiciona o conversor GSON
            .build()
    }

    // Cria e expõe o serviço da API de Gatos

    val catService: ApiService = getClient(CAT_API_BASE_URL).create(ApiService::class.java)

    // Cria e expõe o serviço da API de Cachorros

    val dogService: ApiService = getClient(DOG_API_BASE_URL).create(ApiService::class.java)
}