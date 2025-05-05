package model

import java.io.File

class ArquivoCsv(nome: String) : Arquivo(nome, "csv") {
    override fun ler(): String {
        val arquivo = File(caminho)
        if (!arquivo.exists()) {
            return "Arquivo CSV não encontrado."
        }

        val linhas = arquivo.readLines()
        if (linhas.isEmpty()) {
            return "Arquivo CSV vazio."
        }

        println("\n=== Conteúdo do CSV ($nome) ===")

        // Determina a largura das colunas
        val colunas = linhas.first().split(",")
        val larguras = colunas.map { it.length }.toMutableList()

        linhas.forEach { linha ->
            linha.split(",").forEachIndexed { i, valor ->
                if (valor.length > larguras[i]) {
                    larguras[i] = valor.length
                }
            }
        }

        // Imprime o CSV formatado como tabela
        linhas.forEachIndexed { i, linha ->
            val valores = linha.split(",")
            valores.forEachIndexed { j, valor ->
                print("| ${valor.padEnd(larguras[j])} ")
            }
            println("|")

            if (i == 0) {
                larguras.forEach { largura ->
                    print("+${"-".repeat(largura + 2)}")
                }
                println("+")
            }
        }

        return "CSV exibido como tabela no terminal."
    }
}