package com.example.animais

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.animais.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var myPreferences: MyPreferences // Instancia o MyPreferences aqui também para a checagem inicial

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        myPreferences = MyPreferences(this) // Inicializa as preferências

        val nomeSalvo = myPreferences.getString(Constants.KEY_USER_NAME) // Lógica de navegação inicial

        if (nomeSalvo.isNotEmpty()) {  // Se o nome já EXISTE, pule direto para a SecondActivity
            irParaSegundaTela()
        } else { // Se o nome NÃO EXISTE, mostre o layout da MainActivity
            setupLayout()
        }
    }

    // Função que pula para a segunda tela
    private fun irParaSegundaTela() {
        val intent = Intent(this, SecondActivity::class.java)
        startActivity(intent)
        finish() // Destrói a MainActivity para que o usuário não possa "voltar" para ela
    }

    // Função para configurar a MainActivity
    private fun setupLayout() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java) // Inicializa o ViewModel (agora é AndroidViewModel)

        binding.buttonGuardar.setOnClickListener {
            val nome = binding.nome.text.toString()
            viewModel.onGuardarClicked(nome)
        }

        setupObservers()
    }
    private fun setupObservers() {

        // Observa o evento de navegação

        viewModel.navigateToSecondActivity.observe(this) { nome ->
            nome?.let {

                // Ao invés de passar o nome, apenas chamamos a função

                irParaSegundaTela()
                viewModel.onNavigationDone()
            }
        }

        // Observa o evento de Toast

        viewModel.toastMessage.observe(this) { message ->
            message?.let {
                toast(it)
                viewModel.onToastShown()
            }
        }
    }
    private fun toast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}