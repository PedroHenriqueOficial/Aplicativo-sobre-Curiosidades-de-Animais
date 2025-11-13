package com.example.animais

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
class SecondViewModel(application: Application) : AndroidViewModel(application) {

    // Declaração das Constantes
    private val MODO_INICIAL = 0
    private val MODO_GATO = 1
    private val MODO_CACHORRO = 2
    private val myPreferences = MyPreferences(application) // Instancia o MyPreferences

    // Listas
    private val listaFrasesGato: List<String>
    private val listaFrasesCachorro: List<String>

    // LiveData - Novo LIVE DATA para o NOME
    private val _nomeUsuario = MutableLiveData<String>()
    val nomeUsuario: LiveData<String> = _nomeUsuario
    private val _animalSelecionado = MutableLiveData<Int>(MODO_INICIAL)
    val animalSelecionado: LiveData<Int> = _animalSelecionado
    private val _fraseGerada = MutableLiveData<String>()
    val fraseGerada: LiveData<String> = _fraseGerada
    private val _toastMessage = MutableLiveData<String?>()
    val toastMessage: LiveData<String?> = _toastMessage

    // Lógica

    init {
        val resources = getApplication<Application>().resources

        listaFrasesGato = resources.getStringArray(R.array.lista_curiosidades_gato).toList()
        listaFrasesCachorro = resources.getStringArray(R.array.lista_curiosidades_cachorro).toList()

        // Ler o nome ao iniciar o ViewModel

        _nomeUsuario.value = myPreferences.getString(Constants.KEY_USER_NAME)
    }
    fun selecionarGato() {
        _animalSelecionado.value = MODO_GATO
        _fraseGerada.value = listaFrasesGato.random()
    }
    fun selecionarCachorro() {
        _animalSelecionado.value = MODO_CACHORRO
        _fraseGerada.value = listaFrasesCachorro.random()
    }
    fun gerarOutraFrase() {
        when (_animalSelecionado.value) {
            MODO_GATO -> {
                _fraseGerada.value = listaFrasesGato.random()
            }
            MODO_CACHORRO -> {
                _fraseGerada.value = listaFrasesCachorro.random()
            }
            else -> {
                val msg = getApplication<Application>().resources.getString(R.string.clique_primeiro)
                _toastMessage.value = msg
            }
        }
    }
    fun onToastMostrado() {
        _toastMessage.value = null
    }
}