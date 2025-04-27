import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.attribute.BasicFileAttributes
import java.nio.file.attribute.FileTime
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.*

abstract class FileAbstract(path: String) {
    private val size: Long
    private val creationTime: FileTime
    private val lastAccessedTime: FileTime
    private val lastModifiedTime: FileTime
    private val exists: Boolean

    init {
        val attributes = try {
            Files.readAttributes(Paths.get(path), BasicFileAttributes::class.java)
        } catch (e: Exception) {
            throw IllegalStateException("Failed to read file attributes for '$path': ${e.message}")
        }

        size = attributes.size()
        creationTime = attributes.creationTime()
        lastAccessedTime = attributes.lastAccessTime()
        lastModifiedTime = attributes.lastModifiedTime()
        exists = true
    }

    private fun formatFileTime(fileTime: FileTime): String {
        val instant = Instant.ofEpochMilli(fileTime.toMillis())
        val zonedDateTime = instant.atZone(ZoneId.systemDefault())

        val dayOfWeek = zonedDateTime.dayOfWeek
            .getDisplayName(TextStyle.FULL, Locale.ENGLISH)

        val day = zonedDateTime.dayOfMonth
        val month = zonedDateTime.month
            .getDisplayName(TextStyle.FULL, Locale.ENGLISH)

        val year = zonedDateTime.year
        val time = zonedDateTime.toLocalTime()
            .format(DateTimeFormatter.ofPattern("HH:mm:ss"))

        return "$dayOfWeek, $day de $month de $year, $time"
    }

    fun getFileCreationTime(): String =
        formatFileTime(creationTime)
    fun getFilelastAccessedTime(): String =
        formatFileTime(lastAccessedTime)
    fun getFilelastModifiedTime(): String =
        formatFileTime(lastModifiedTime)

    abstract fun readContent(): String
    //abstract fun typeSupported(): Boolean
}