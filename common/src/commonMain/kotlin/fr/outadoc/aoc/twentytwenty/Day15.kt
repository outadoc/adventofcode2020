package fr.outadoc.aoc.twentytwenty

import fr.outadoc.aoc.scaffold.Day
import fr.outadoc.aoc.scaffold.Year

class Day15 : Day(Year.TwentyTwenty) {

    private val startingNumbers: List<Long> =
        readDayInput()
            .split(',')
            .map { it.toLong() }

    private val numberSequence: Sequence<Long> = sequence {
        val lastSpokenAtIndexMap = mutableMapOf<Long, Int>()
        var lastSpokenItem: Long = 0

        startingNumbers.forEachIndexed { i, n ->
            lastSpokenAtIndexMap[lastSpokenItem] = i - 1
            lastSpokenItem = n
            yield(n)
        }

        var i: Int = startingNumbers.size
        while (true) {
            val n = lastSpokenAtIndexMap[lastSpokenItem]?.let { (i - it - 1).toLong() } ?: 0L

            lastSpokenAtIndexMap[lastSpokenItem] = i - 1
            lastSpokenItem = n

            yield(n)
            i++
        }
    }

    private fun elementAt(pos: Int): Long =
        numberSequence.elementAt(pos - 1)

    fun step1(): Long = elementAt(2020)

    fun step2(): Long = elementAt(30000000)
}