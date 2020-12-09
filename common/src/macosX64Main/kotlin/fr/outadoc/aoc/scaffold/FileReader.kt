package fr.outadoc.aoc.scaffold

import kotlinx.cinterop.*
import platform.Foundation.*

actual class FileReader {

    companion object {
        const val BASE_DIR = "src/commonTest/resources"
    }

    actual fun readInput(filename: String): String = memScoped {
        val path = "$BASE_DIR/$filename"

        NSFileManager().apply {
            if (!fileExistsAtPath(path)) {
                throw Exception("File does not exist: $path, current dir = $currentDirectoryPath")
            }
        }

        val nsError = alloc<ObjCObjectVar<NSError?>>()
        return NSString.stringWithContentsOfFile(
            path = path,
            encoding = NSUTF8StringEncoding,
            error = nsError.ptr
        ).let { content ->
            val error = nsError.value
            if (content == null) throw NSErrorException(error)
            content
        }
    }
}