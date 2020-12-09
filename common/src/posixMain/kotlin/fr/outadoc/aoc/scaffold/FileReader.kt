package fr.outadoc.aoc.scaffold

import kotlinx.cinterop.*
import platform.posix.*

actual class FileReader {

    companion object {
        private const val BASE_DIR = "src/commonTest/resources"
    }

    actual fun readInput(filename: String): String = memScoped {
        val path = "$BASE_DIR/$filename"

        // Open file
        val f = fopen(path, "r")

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