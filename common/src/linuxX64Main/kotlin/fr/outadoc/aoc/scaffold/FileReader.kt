package fr.outadoc.aoc.scaffold

actual class FileReader {

    companion object {
        const val BASE_DIR = "src/commonTest/resources"
    }

    actual fun readInput(filename: String): String {
        val path = "$BASE_DIR/$filename"
        TODO()
    }
}