package com.example.animais

import android.graphics.Color
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.example.animais.databinding.ActivitySecondBinding

class SecondActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySecondBinding
    private lateinit var viewModel: SecondViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(SecondViewModel::class.java)

        // Configura os cliques (sem mudança)

        binding.gato.setOnClickListener {
            viewModel.selecionarGato()
        }
        binding.cachorro.setOnClickListener {
            viewModel.selecionarCachorro()
        }
        binding.buttonGuardar.setOnClickListener {
            viewModel.gerarOutraFrase()
        }

        setupObservers()
    }
    private fun setupObservers() {

        viewModel.nomeUsuario.observe(this) { nome ->
            binding.mensagem.text = getString(R.string.saudacao, nome)
        }

        viewModel.fraseGerada.observe(this) { frase ->
            binding.textFraseGerada.text = frase
        }

        // Observador para o LOADING

        viewModel.isLoading.observe(this) { isLoading ->
            if (isLoading) {

                // Mostra o "loading" e esconde o texto

                binding.progressBar.visibility = android.view.View.VISIBLE
                binding.textFraseGerada.visibility = android.view.View.GONE

                // Desabilita os botões para evitar cliques duplos

                binding.buttonGuardar.isEnabled = false
                binding.gato.isEnabled = false
                binding.cachorro.isEnabled = false
            } else {

                // Esconde o "loading" e mostra o texto

                binding.progressBar.visibility = android.view.View.GONE
                binding.textFraseGerada.visibility = android.view.View.VISIBLE

                // Habilita os botões novamente

                binding.buttonGuardar.isEnabled = true
                binding.gato.isEnabled = true
                binding.cachorro.isEnabled = true
            }
        }

        // Observa o animal selecionado (sem mudança)

        viewModel.animalSelecionado.observe(this) { modoAtual ->
            val corSelecionada = ContextCompat.getColor(this, R.color.yellow)
            val corPadrao = Color.WHITE

            when (modoAtual) {
                1 -> {
                    binding.gato.setColorFilter(corSelecionada)
                    binding.cachorro.setColorFilter(corPadrao)
                }
                2 -> {
                    binding.gato.setColorFilter(corPadrao)
                    binding.cachorro.setColorFilter(corSelecionada)
                }
                else -> {
                    binding.gato.setColorFilter(corPadrao)
                    binding.cachorro.setColorFilter(corPadrao)
                }
            }
        }

        // Observa mensagens de Toast (sem mudança)

        viewModel.toastMessage.observe(this) { mensagem ->
            mensagem?.let {
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
                viewModel.onToastMostrado()
            }
        }
    }
}