package fr.outadoc.aoc.twentytwentyone

import fr.outadoc.aoc.scaffold.Day
import fr.outadoc.aoc.scaffold.readDayInput
import kotlin.math.abs

class Day07 : Day<Int> {

    private val input = readDayInput()
        .split(',')
        .map { it.toInt() }
        .sorted()

    private fun List<Int>.median() = (this[size / 2] + this[(size - 1) / 2]) / 2

    override fun step1(): Int {
        val targetPos = input.median()
        return input.sumOf { crabPos -> abs(targetPos - crabPos) }
    }

    override fun step2(): Int {
        val possiblePositions = input.first()..input.last()
        return possiblePositions.minOf { targetPos ->
            // Look for the minimum amount of fuel we need
            input.sumOf { crabPos -> triang(abs(targetPos - crabPos)) }
        }
    }

    /**
     * According to the sample, we're looking for a function `f` such that:
     *
     * f(1) = 1
     * f(2) = 3
     * f(3) = 6
     * f(4) = 10
     * f(5) = 15
     * f(9) = 45
     * f(11) = 66
     *
     * Google tells me these are triangular numbers:
     * https://en.wikipedia.org/wiki/Triangular_number
     */
    private fun triang(n: Int): Int = (n * (n + 1)) / 2

    override val expectedStep1 = 341_534
    override val expectedStep2 = 93_397_632
}
