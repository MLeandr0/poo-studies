package model

import java.io.File

class ArquivoJson(nome: String) : Arquivo(nome, "json") {
    override fun ler(): String {
        val arquivo = File(caminho)
        if (!arquivo.exists()) {
            return "Arquivo JSON não encontrado."
        }

        val conteudo = arquivo.readText()

        println("\n=== Conteúdo do JSON ($nome) ===")
        println(formatarJsonSimples(conteudo))

        return "Conteúdo JSON exibido no terminal."
    }

    private fun formatarJsonSimples(json: String): String {
        var indent = 0
        val builder = StringBuilder()
        var dentroString = false

        for (char in json) {
            when {
                char == '"' -> dentroString = !dentroString
                !dentroString && (char == '{' || char == '[') -> {
                    builder.append("$char\n${"  ".repeat(++indent)}")
                }
                !dentroString && (char == '}' || char == ']') -> {
                    builder.append("\n${"  ".repeat(--indent)}$char")
                }
                !dentroString && char == ',' -> {
                    builder.append(",\n${"  ".repeat(indent)}")
                }
                !dentroString && char == ':' -> {
                    builder.append(": ")
                }
                else -> builder.append(char)
            }
        }

        return builder.toString()
    }

    override fun alterar(conteudoNovo: String) {
        try {
            // Validação básica de JSON
            val trimmed = conteudoNovo.trim()
            if (!(trimmed.startsWith("{") && trimmed.endsWith("}")) &&
                !(trimmed.startsWith("[") && trimmed.endsWith("]"))) {
                throw IllegalArgumentException("JSON deve começar com { ou [ e terminar com } ou ]")
            }

            File(caminho).writeText(conteudoNovo)
            println("JSON alterado com sucesso!")
        } catch (e: Exception) {
            println("Erro ao alterar JSON: ${e.message}")
        }
    }
}