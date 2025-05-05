package model

import java.io.File

class ArquivoTxt(nome: String) : Arquivo(nome, "txt") {
    override fun ler(): String {
        val arquivo = File(caminho)
        if (!arquivo.exists()) {
            return "Arquivo TXT não encontrado."
        }

        val linhas = arquivo.readLines()

        println("\n=== Conteúdo do TXT ($nome) ===")

        if (linhas.isEmpty()) {
            println("(arquivo vazio)")
        } else {
            val numDigitos = linhas.size.toString().length
            linhas.forEachIndexed { i, linha ->
                println("${(i + 1).toString().padStart(numDigitos)}: $linha")
            }
        }

        return "Texto exibido com numeração de linhas."
    }
}