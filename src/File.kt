import java.io.File
import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.attribute.PosixFileAttributes

abstract class File(protected val path: String) {
    private var size: Long = 0
        private set

    protected lateinit var owner: String
    protected var isOpen: Boolean = false
        private set

    protected val extension: String = path.substringAfterLast('.', "").lowercase()

    //public fun readContent() {}
    //public fun readProperties() {}

    fun loadMetadata(): Boolean {
        return try {
            val file = File(path)
            if(!file.exists()) return false

            val tributes = Files.readAttributes(Paths.get(path), PosixFileAttributes::class.java)

            this.size = tributes.size()
            this.owner = tributes.owner().name
            true
        } catch (e: Exception) {
            false
        }
    }

    fun openFile(): Boolean {
        if (!loadMetadata()) {
            println("Error loading metadata of files $path")
            return false
        }

        isOpen = true
        println("File $path open sucesufully. Size: $size bytes")
        return true
    }

    fun closeFile() {
        isOpen = false
        println("File $path closed")
    }

    fun getSize(): Long = size
    fun getPath(): String = path
    fun getOwner(): String = owner

    abstract fun readContent(): String
    abstract fun typeSupported(): Boolean
}