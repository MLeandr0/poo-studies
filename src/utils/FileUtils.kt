package util

import java.io.File

object FileUtils {

    fun listarArquivos(extensao: String): List<String> {
        val pasta = File("arquivos")
        if (!pasta.exists()) {
            pasta.mkdirs()
        }
        val arquivos = pasta.listFiles { _, nome -> nome.endsWith(".$extensao") }?.map { it.name } ?: emptyList()

        if (arquivos.isEmpty()) {
            println("Nenhum arquivo .$extensao encontrado.")
        }
        return arquivos
    }
}
