package fr.outadoc.aoc.twentytwentyone

import fr.outadoc.aoc.scaffold.Day
import fr.outadoc.aoc.scaffold.readDayInput

class Day01 : Day<Int> {

    val input = readDayInput()
        .lineSequence()
        .map { it.toInt() }

    override fun step1(): Int {
        return input
            .drop(1)
            .fold(0 to input.first()) { (acc, previous), current ->
                (if (current > previous) acc + 1 else acc) to current
            }
    }

    override fun step2(): Int = -1

    override val expectedStep1: Int = -1
    override val expectedStep2: Int = -1
}
