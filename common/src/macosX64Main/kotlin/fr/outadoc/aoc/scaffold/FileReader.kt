package fr.outadoc.aoc.scaffold

import kotlinx.cinterop.*
import platform.Foundation.*

actual class FileReader actual constructor() {

    companion object {
        const val BASE_DIR = "src/commonTest/resources"
    }

    actual fun readInput(filename: String): String {
        val path = "$BASE_DIR/$filename"

        val nsFileManager = NSFileManager()
        if (!nsFileManager.fileExistsAtPath(path)) {
            throw Exception("File does not exist: $path, current dir = ${nsFileManager.currentDirectoryPath}")
        }

        val nsError = nativeHeap.alloc<ObjCObjectVar<NSError?>>()
        return NSString.stringWithContentsOfFile(
            path = path,
            encoding = NSUTF8StringEncoding,
            error = nsError.ptr
        ).let { content ->
            val error = nsError.value
            nativeHeap.free(nsError)
            if (content == null) throw NSErrorException(error)
            content
        }
    }
}