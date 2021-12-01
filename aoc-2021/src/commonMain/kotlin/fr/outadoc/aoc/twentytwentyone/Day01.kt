package fr.outadoc.aoc.twentytwentyone

import fr.outadoc.aoc.scaffold.Day
import fr.outadoc.aoc.scaffold.readDayInput

class Day01 : Day<Int> {

    private val input = readDayInput()
        .lineSequence()
        .map { it.toInt() }

    override fun step1(): Int {
        return input
            .windowed(size = 2, step = 1)
            .map { it[0] to it[1] }
            .fold(0) { acc, (prev, current) ->
                if (current > prev) acc + 1 else acc
            }
    }

    override fun step2(): Int = -1

    override val expectedStep1: Int = -1
    override val expectedStep2: Int = -1
}
