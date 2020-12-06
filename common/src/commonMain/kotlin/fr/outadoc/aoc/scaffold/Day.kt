package fr.outadoc.aoc.scaffold

abstract class Day(val year: Year) {

    abstract fun step1(): Long
    abstract fun step2(): Long

    protected inline fun <reified T> T.readDayInput(): String {
        val year = year.id
        val day = this!!::class.simpleName!!
        return FileReader().readInput("$year/$day.txt")
    }
}