package fr.outadoc.aoc.scaffold

import kotlinx.cinterop.*
import platform.Foundation.*

actual class FileReader actual constructor() {
    actual fun readInput(filename: String): String {
        val nsError = nativeHeap.alloc<ObjCObjectVar<NSError?>>()
        return NSString.stringWithContentsOfFile(
            path = filename,
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