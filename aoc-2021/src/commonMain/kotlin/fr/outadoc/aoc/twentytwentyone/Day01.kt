package fr.outadoc.aoc.twentytwentyone

import fr.outadoc.aoc.scaffold.Day
import fr.outadoc.aoc.scaffold.readDayInput

class Day01 : Day<Int> {

    private val input = readDayInput()
        .lineSequence()
        .map { it.toInt() }

    override fun step1() = input
        .windowed(size = 2, step = 1)
        .map { it[0] to it[1] }
        .fold(0) { acc, (prev, current) ->
            if (current > prev) acc + 1 else acc
        }

    override fun step2() = input
        .windowed(size = 3, step = 1)
        .map { it.sum() }
        .windowed(size = 2, step = 1)
        .map { it[0] to it[1] }
        .fold(0) { acc, (prev, current) ->
            if (current > prev) acc + 1 else acc
        }

    override val expectedStep1: Int = 1451
    override val expectedStep2: Int = 1395
}
