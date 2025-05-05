package model

import `interface`.Gerenciavel
import java.io.File

abstract class Arquivo(
    protected val nome: String,
    protected val extensao: String
) : Gerenciavel {

    protected val caminho: String = "arquivos/$nome.$extensao"

    override fun criar() {
        val arquivo = File(caminho)
        if (arquivo.createNewFile()) {
            println("Arquivo $caminho criado com sucesso!")
        } else {
            println("Arquivo $caminho já existe.")
        }
    }

    abstract override fun ler(): String

//    override fun ler(): String {
//        val arquivo = File(caminho)
//        if (arquivo.exists()) {
//            val builder = ProcessBuilder("notepad", arquivo.absolutePath)
//            val process = builder.start()
//            process.waitFor()
//            return "Arquivo foi aberto no Bloco de Notas."
//        }
//        return "Arquivo não encontrado."
//    }

    override fun alterar(conteudoNovo: String) {
        val arquivo = File(caminho)
        if (arquivo.exists()) {
            val builder = ProcessBuilder("notepad", arquivo.absolutePath)
            val process = builder.start()
            println("Arquivo aberto para edição no Bloco de Notas.")
            process.waitFor()
        } else {
            println("Arquivo não encontrado.")
        }
    }

    override fun deletar() {
        val arquivo = File(caminho)
        if (arquivo.delete()) {
            println("Arquivo $caminho deletado com sucesso!")
        } else {
            println("Arquivo não encontrado ou erro ao deletar.")
        }
    }
}
