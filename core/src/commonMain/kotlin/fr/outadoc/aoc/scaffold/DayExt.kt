package fr.outadoc.aoc.scaffold

fun <T: Day> T.readDayInput(year: String): String {
    val day = this::class.simpleName!!
    return FileReader().readInput("$year/$day.txt").trimEnd()
}
