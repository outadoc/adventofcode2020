package fr.outadoc.aoc.scaffold

expect class FileReader() {
    fun readInput(filename: String): String
}