package util

import java.io.File

object FileUtils {

    private val pasta = File("arquivos")
    fun criarPasta() {
        if (!pasta.exists()) {
            pasta.mkdirs()
        }
    }

    fun listarArquivos(extensao: String): List<String> {
        criarPasta()
        val arquivos = pasta.listFiles { _, nome -> nome.endsWith(".$extensao") }?.map { it.name } ?: emptyList()

        if (arquivos.isEmpty()) {
            println("Nenhum arquivo .$extensao encontrado.")
        }
        return arquivos
    }
}
