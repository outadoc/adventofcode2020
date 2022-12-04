package fr.outadoc.aoc.twentytwentytwo

import fr.outadoc.aoc.scaffold.Day
import fr.outadoc.aoc.scaffold.readDayInput

class Day04 : Day<Int> {

    data class Assignment(val a: IntRange, val b: IntRange)

    private val input = readDayInput()
        .lineSequence()
        .map { line ->
            val (a, b) = line.split(',')
            Assignment(
                a = a.asRange(),
                b = b.asRange()
            )
        }

    private fun String.asRange(): IntRange {
        val (start, end) = split('-')
        return start.toInt()..end.toInt()
    }

    private fun IntRange.fullyContains(other: IntRange): Boolean {
        return first <= other.first && last >= other.last
    }

    override fun step1(): Int =
        input.count { assignment ->
            assignment.a.fullyContains(assignment.b) || assignment.b.fullyContains(assignment.a)
        }

    override val expectedStep1 = 524

    override fun step2(): Int =
        input.count { assignment ->
            assignment.a.intersect(assignment.b).isNotEmpty()
        }

    override val expectedStep2 = 798
}
