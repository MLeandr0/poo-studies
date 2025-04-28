import service.GerenciadorArquivos

fun main() {
    val gerenciador = GerenciadorArquivos()

    while (true) {
        println("\n=== Sistema de Gerenciamento de Arquivos ===")
        gerenciador.menuPrincipal()
    }
}
