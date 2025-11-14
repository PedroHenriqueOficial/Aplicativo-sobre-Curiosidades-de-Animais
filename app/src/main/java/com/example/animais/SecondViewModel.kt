package com.example.animais

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import retrofit2.Call       // Importe o Call do Retrofit
import retrofit2.Callback   // Importe o Callback do Retrofit
import retrofit2.Response
class SecondViewModel(application: Application) : AndroidViewModel(application) {

    // Constantes
    private val MODO_INICIAL = 0
    private val MODO_GATO = 1
    private val MODO_CACHORRO = 2
    private val myPreferences = MyPreferences(application)

    // LiveData
    private val _nomeUsuario = MutableLiveData<String>()
    val nomeUsuario: LiveData<String> = _nomeUsuario
    private val _animalSelecionado = MutableLiveData<Int>(MODO_INICIAL)
    val animalSelecionado: LiveData<Int> = _animalSelecionado
    private val _fraseGerada = MutableLiveData<String>()
    val fraseGerada: LiveData<String> = _fraseGerada
    private val _toastMessage = MutableLiveData<String?>()
    val toastMessage: LiveData<String?> = _toastMessage

    // LiveData para o estado de "Carregando"
    private val _isLoading = MutableLiveData<Boolean>(false)
    val isLoading: LiveData<Boolean> = _isLoading

    init {
        _nomeUsuario.value = myPreferences.getString(Constants.KEY_USER_NAME)
    }

    // Função chamada pelo clique no Gato
    fun selecionarGato() {
        _animalSelecionado.value = MODO_GATO
        buscarFraseDeGato() // Chama a nova função da API
    }

    // Função chamada pelo clique no Cachorro
    fun selecionarCachorro() {
        _animalSelecionado.value = MODO_CACHORRO
        buscarFraseDeCachorro() // Chama a nova função da API
    }

    // Função chamada pelo clique no Botão "Gerar"
    fun gerarOutraFrase() {

        if (_isLoading.value == true) return // Se já estiver caregada, não faz nada

        when (_animalSelecionado.value) {
            MODO_GATO -> {
                buscarFraseDeGato()
            }
            MODO_CACHORRO -> {
                buscarFraseDeCachorro()
            }
            else -> {
                val msg = getApplication<Application>().resources.getString(R.string.clique_primeiro)
                _toastMessage.value = msg
            }
        }
    }

    // Funções da API
    private fun buscarFraseDeGato() {
        _isLoading.value = true

        // Pega o serviço do nosso NetworkClient e enfileira a chamada

        NetworkClient.catService.getCatFact().enqueue(object : Callback<CatFactEntity> {

            // Chamando em caso de SUCESSO

            override fun onResponse(call: Call<CatFactEntity>, response: Response<CatFactEntity>) {
                if (response.isSuccessful) {

                    // Pega o corpo da resposta e atualiza o LiveData

                    _fraseGerada.value = response.body()?.fact
                } else {
                    _toastMessage.value = "Falha ao buscar frase de gato."
                }
                _isLoading.value = false // Para de carregar
            }

            // Chamando em caso de FALHA (ex: sem internet)

            override fun onFailure(call: Call<CatFactEntity>, t: Throwable) {
                _toastMessage.value = "Erro de rede: ${t.message}"
                _isLoading.value = false // Para de carregar
            }
        })
    }
    private fun buscarFraseDeCachorro() {
        _isLoading.value = true
        NetworkClient.dogService.getDogFact().enqueue(object : Callback<DogFactEntity> {

            override fun onResponse(call: Call<DogFactEntity>, response: Response<DogFactEntity>) {
                if (response.isSuccessful) {

                    // A API de cachorro retorna uma LISTA, logo pegamos o primeiro item

                    _fraseGerada.value = response.body()?.facts?.firstOrNull()
                } else {
                    _toastMessage.value = "Falha ao buscar frase de cachorro."
                }
                _isLoading.value = false
            }

            override fun onFailure(call: Call<DogFactEntity>, t: Throwable) {
                _toastMessage.value = "Erro de rede: ${t.message}"
                _isLoading.value = false
            }
        })
    }
    fun onToastMostrado() {
        _toastMessage.value = null
    }
}