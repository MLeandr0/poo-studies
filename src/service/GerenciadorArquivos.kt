package service

import model.*
import util.FileUtils

class GerenciadorArquivos {

    fun menuPrincipal() {
        println("Escolha o tipo de arquivo:")
        println("1 - TXT")
        println("2 - CSV")
        println("3 - JSON")
        print("Opção: ")
        val tipo = readlnOrNull()?.toIntOrNull()

        val extensao = when (tipo) {
            1 -> "txt"
            2 -> "csv"
            3 -> "json"
            else -> {
                println("Tipo inválido.")
                return
            }
        }

        menuCrud(extensao)
    }

    private fun menuCrud(extensao: String) {
        while (true) {
            println("\n--- MENU $extensao ---")
            println("1 - Criar Arquivo")
            println("2 - Ler Arquivo")
            println("3 - Alterar Arquivo")
            println("4 - Deletar Arquivo")
            println("5 - Voltar")
            print("Opção: ")
            when (readlnOrNull()?.toIntOrNull()) {
                1 -> criarArquivo(extensao)
                2 -> lerArquivo(extensao)
                3 -> alterarArquivo(extensao)
                4 -> deletarArquivo(extensao)
                5 -> return
                else -> println("Opção inválida!")
            }
        }
    }

    private fun criarArquivo(extensao: String) {
        FileUtils.criarPasta()
        print("Digite o nome do arquivo (sem extensão): ")
        val nome = readlnOrNull() ?: return
        val arquivo = criarInstancia(nome, extensao)
        arquivo.criar()
    }

    private fun lerArquivo(extensao: String) {
        val arquivos = FileUtils.listarArquivos(extensao)
        if (arquivos.isEmpty()) return
        val arquivo = escolherArquivo(arquivos, extensao)
        println(arquivo.ler())
    }

    private fun alterarArquivo(extensao: String) {
        val arquivos = FileUtils.listarArquivos(extensao)
        if (arquivos.isEmpty()) return
        val arquivo = escolherArquivo(arquivos, extensao)
        val novoConteudo = readlnOrNull() ?: return
        arquivo.alterar(novoConteudo)
    }

    private fun deletarArquivo(extensao: String) {
        val arquivos = FileUtils.listarArquivos(extensao)
        if (arquivos.isEmpty()) return
        val arquivo = escolherArquivo(arquivos, extensao)
        arquivo.deletar()
    }

    private fun escolherArquivo(arquivos: List<String>, extensao: String): model.Arquivo {
        arquivos.forEachIndexed { index, nome ->
            println("${index + 1} - $nome")
        }
        print("Escolha o número do arquivo: ")
        val escolha = readlnOrNull()?.toIntOrNull()?.minus(1) ?: 0
        val nomeSemExtensao = arquivos[escolha].substringBefore(".")
        return criarInstancia(nomeSemExtensao, extensao)
    }

    private fun criarInstancia(nome: String, extensao: String): model.Arquivo {
        return when (extensao) {
            "txt" -> ArquivoTxt(nome)
            "csv" -> ArquivoCsv(nome)
            "json" -> ArquivoJson(nome)
            else -> throw IllegalArgumentException("Extensão inválida.")
        }
    }
}
