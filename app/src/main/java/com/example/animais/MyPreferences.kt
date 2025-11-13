package com.example.animais

import android.content.Context
import android.content.SharedPreferences

class MyPreferences(context: Context) {
    private val sp: SharedPreferences = context.getSharedPreferences(Constants.PREFERENCES_KEY, Context.MODE_PRIVATE)

    fun setString(key: String, str: String) {
        sp.edit().putString(key, str).apply()
    }

    fun getString(key: String): String {
        return sp.getString(key, "") ?: "" // Usando o operador Elvis (?:) para garantir que nunca retorne nulo
    }
}