package com.example.animais

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val myPreferences = MyPreferences(application) // Instancia nossa classe de preferências
    private val _navigateToSecondActivity = MutableLiveData<String?>() // LiveData para navegação (sem mudanças)
    val navigateToSecondActivity: LiveData<String?> = _navigateToSecondActivity
    private val _toastMessage = MutableLiveData<String?>() // LiveData para Toast (sem mudanças)
    val toastMessage: LiveData<String?> = _toastMessage
    fun onGuardarClicked(nome: String) {

        if (nome.isEmpty()) {
            _toastMessage.value = "Preencha o nome."
        } else {
            myPreferences.setString(Constants.KEY_USER_NAME, nome) // Salva o nome permanentemente

            _navigateToSecondActivity.value = nome // Envia o evento de navegação
        }
    }
    fun onNavigationDone() {
        _navigateToSecondActivity.value = null
    }
    fun onToastShown() {
        _toastMessage.value = null
    }
}