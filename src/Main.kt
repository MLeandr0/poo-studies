package app

import `interface`.Gerenciavel
import service.GerenciadorArquivos
import java.util.Scanner

fun main() {
    val scanner = Scanner(System.`in`)
    val gerenciador = GerenciadorArquivos()

    while (true) {
        println("\nEscolha o tipo de arquivo (txt, csv, json) ou digite 'sair':")
        val tipo = scanner.nextLine().lowercase()
        if (tipo == "sair") break

        // Valida a extensão antes de continuar
        if (tipo != "txt" && tipo != "csv" && tipo != "json") {
            println("Tipo inválido. Tente novamente.")
            continue
        }

        val arquivos = gerenciador.listarArquivosPorTipo(tipo)

        if (arquivos.isEmpty()) {
            println("Nenhum arquivo .$tipo encontrado.")
            println("Digite o nome do novo arquivo:")
        } else {
            println("\nArquivos .$tipo disponíveis:")
            arquivos.forEachIndexed { index, name -> println("${index + 1}. $name") }
            println("Digite o nome do arquivo que deseja manipular")
        }

        val nome = scanner.nextLine()
        if (nome.isBlank()) continue

        val arquivo = gerenciador.criarInstancia(nome, tipo)

        if (arquivo != null) {
            menuCrud(arquivo, tipo, gerenciador)
        } else {
            println("Erro ao criar instância para o tipo $tipo.")
        }
    }

    println("Programa encerrado.")
}


private fun menuCrud(arquivo: Gerenciavel, extensao: String, gerenciador: GerenciadorArquivos) {
    val scanner = Scanner(System.`in`)
    while (true) {
        val arquivosExistentes = gerenciador.listarArquivosPorTipo(extensao)

        println("\n--- MENU $extensao ---")

        if (arquivosExistentes.isEmpty()) {
            println("Nenhum arquivo .$extensao encontrado.")
            println("1 - Criar Novo Arquivo")
            println("5 - Voltar")
            print("Opção: ")
            when (readlnOrNull()?.toIntOrNull()) {
                1 -> arquivo.criar()
                5 -> return
                else -> println("Opção inválida!")
            }
        } else {
            println("1 - Criar Novo Arquivo")
            println("2 - Ler Arquivo")
            println("3 - Alterar Arquivo")
            println("4 - Deletar Arquivo")
            println("5 - Voltar")
            print("Opção: ")
            when (readlnOrNull()?.toIntOrNull()) {
                1 -> arquivo.criar()
                2 -> println(arquivo.ler())
                3 -> {
                    println("Faça sua alteração: ")
                    arquivo.alterar(scanner.nextLine())
                }
                4 -> arquivo.deletar()
                5 -> return
                else -> println("Opção inválida!")
            }
        }
    }
}
