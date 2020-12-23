package fr.outadoc.aoc.twentytwenty

import fr.outadoc.aoc.scaffold.Day
import fr.outadoc.aoc.scaffold.Year

class Day23 : Day(Year.TwentyTwenty) {

    private data class State(val cups: List<Int>)

    private val initialState: State =
        readDayInput()
            .lines()
            .first()
            .map { it.toInt() }
            .let { State(it) }

    fun step1(): Long {
        TODO()
    }

    fun step2(): Long {
        TODO()
    }
}