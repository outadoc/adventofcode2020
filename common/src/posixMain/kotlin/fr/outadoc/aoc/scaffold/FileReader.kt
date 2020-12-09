package fr.outadoc.aoc.scaffold

import kotlinx.cinterop.*
import platform.posix.*

actual class FileReader {

    companion object {
        private const val BASE_DIR = "src/commonTest/resources"
    }

    actual fun readInput(filename: String): String {
        val path = "$BASE_DIR/$filename"

        // Open file
        return fopen(path, "r").use { f ->
            f.readToString()
        }
    }

    private fun <T> CPointer<FILE>?.use(block: (CPointer<FILE>) -> T): T {
        checkNotNull(this) {
            "Unable to open file. errno = $errno"
        }

        return block(this).also {
            fclose(this)
        }
    }

    private fun CPointer<FILE>.readToString(): String = memScoped {
        val f = this@readToString

        // Count bytes
        fseek(f, 0, SEEK_END)
        val fileSize = ftell(f)
        fseek(f, 0, SEEK_SET)

        // Allocate buffer
        val buffer = allocArray<ByteVar>(fileSize + 1)

        // Read file to buffer
        fread(buffer, 1, fileSize.toULong(), f)
        fclose(f)
        buffer[fileSize] = 0

        return buffer.toKStringFromUtf8()
    }
}