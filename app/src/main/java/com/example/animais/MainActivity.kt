package com.example.animais

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.animais.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonGuardar.setOnClickListener { // Define o que acontece ao clicar no botão "GUARDAR"

            val nome = binding.nome.text.toString()

            if(nome.isEmpty()) {
                toast("Preencha o nome.")
            } else {
                val intent = Intent(this, SecondActivity::class.java) // Abre a SecondActivity

                intent.putExtra("NOME", nome) // A chave "NOME" é como um rótulo para a caixa

                startActivity(intent) // Inicia a nova tela
            }
        }
    }

    private fun toast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
