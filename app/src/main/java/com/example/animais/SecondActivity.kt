package com.example.animais

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.animais.databinding.ActivitySecondBinding

class SecondActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySecondBinding

    // Variáveis para guardar as listas de frases
    private lateinit var listaFrasesGato: List<String>
    private lateinit var listaFrasesCachorro: List<String>

    // Constantes para definir o "modo"
    private val MODO_INICIAL = 0
    private val MODO_GATO = 1
    private val MODO_CACHORRO = 2

    // Variável de estado para "lembrar" o último animal clicado
    private var modoAtual = MODO_INICIAL

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Carrega as listas de frases do strings.xml

        listaFrasesGato = resources.getStringArray(R.array.lista_curiosidades_gato).toList()
        listaFrasesCachorro = resources.getStringArray(R.array.lista_curiosidades_cachorro).toList()

        // Recebe o nome e exibe a saudação inicial

        val nome = intent.getStringExtra("NOME")
        binding.mensagem.text = getString(R.string.saudacao, nome)

        // Define o clique da imagem do GATO

        binding.gato.setOnClickListener {
            // Define o modo para "gato"
            modoAtual = MODO_GATO
            // Sorteia uma frase de gato
            binding.textFraseGerada.text = listaFrasesGato.random()
        }

        // Define o clique da imagem do CACHORRO

        binding.cachorro.setOnClickListener {
            // Define o modo para "cachorro"
            modoAtual = MODO_CACHORRO
            // Sorteia uma frase de cachorro
            binding.textFraseGerada.text = listaFrasesCachorro.random()
        }

        // Define o clique do botão "GERAR OUTRA FRASE"

        binding.buttonGuardar.setOnClickListener {

            // Verifica qual modo está ativo

            when (modoAtual) {
                MODO_GATO -> {
                    // Se o modo for gato, sorteia outra frase de gato
                    binding.textFraseGerada.text = listaFrasesGato.random()
                }
                MODO_CACHORRO -> {
                    // Se o modo for cachorro, sorteia outra frase de cachorro
                    binding.textFraseGerada.text = listaFrasesCachorro.random()
                }
                else -> {
                    // Se nenhum animal foi clicado ainda, avisa o usuário
                    Toast.makeText(this, getString(R.string.clique_primeiro), Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
