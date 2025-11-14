package com.example.animais

import com.google.gson.annotations.SerializedName

// Mapeia a resposta da API de cachorros

data class DogFactEntity(
    @SerializedName("facts")
    val facts: List<String>, // O fato vem dentro de uma lista

    @SerializedName("success")
    val success: Boolean
)