package fr.outadoc.aoc.scaffold

actual class FileReader {
    actual fun readInput(filename: String): String {
        return this::class.java.classLoader!!.let { loader ->
            loader.getResource(filename)!!.readText().trimEnd()
        }
    }
}