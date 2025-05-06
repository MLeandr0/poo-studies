package service

import model.*

class GerenciadorArquivos {

    fun criarInstancia(nome: String, tipo: String) = when (tipo.lowercase()) {
        "txt" -> ArquivoTxt(nome)
        "csv" -> ArquivoCsv(nome)
        "json" -> ArquivoJson(nome)
        else -> null
    }

    fun listarArquivosPorTipo(tipo: String): List<String> {
        val pasta = java.io.File("arquivos")
        if (!pasta.exists()) pasta.mkdirs()

        return pasta.listFiles { _, nome -> nome.endsWith(".$tipo") }
            ?.map { it.nameWithoutExtension }
            ?: emptyList()
    }
}
