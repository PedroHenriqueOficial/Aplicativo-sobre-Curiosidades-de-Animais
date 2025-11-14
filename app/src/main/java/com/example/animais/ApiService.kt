package com.example.animais

import retrofit2.Call
import retrofit2.http.GET

// Interface que define os endpoints da API

interface ApiService {

    // Define o método GET para o endpoint "fact" (da API de gatos)

    @GET("fact")
    fun getCatFact(): Call<CatFactEntity>

    // Define o método GET para o endpoint "api/facts" (da API de cachorros)

    @GET("api/facts")
    fun getDogFact(): Call<DogFactEntity>
}