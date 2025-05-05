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

    override fun alterar(conteudoNovo: String) {
        try {
            // Validação básica de CSV
            val linhas = conteudoNovo.lines()
            if (linhas.size > 1) {
                val numColunas = linhas.first().split(",").size
                linhas.drop(1).forEachIndexed { i, linha ->
                    if (linha.split(",").size != numColunas) {
                        throw IllegalArgumentException("Linha ${i+2} tem número diferente de colunas")
                    }
                }
            }

            File(caminho).writeText(conteudoNovo)
            println("CSV alterado com sucesso!")
        } catch (e: Exception) {
            println("Erro ao alterar CSV: ${e.message}")
        }
    }
}