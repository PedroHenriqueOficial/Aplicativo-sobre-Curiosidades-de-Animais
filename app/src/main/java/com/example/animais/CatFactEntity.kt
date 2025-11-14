package com.example.animais

import com.google.gson.annotations.SerializedName

// Mapeia a resposta da API de gatos

data class CatFactEntity(
    @SerializedName("fact")
    val fact: String,

    @SerializedName("length")
    val length: Int
)