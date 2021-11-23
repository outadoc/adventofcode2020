package fr.outadoc.aoc.scaffold

fun <T, U : Day<T>> U.readDayInput(year: String): String {
    val day = this::class.simpleName!!
    return FileReader().readInput("$year/$day.txt").trimEnd()
}
