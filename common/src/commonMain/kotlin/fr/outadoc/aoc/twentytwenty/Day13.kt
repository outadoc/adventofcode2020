package fr.outadoc.aoc.twentytwenty

import fr.outadoc.aoc.scaffold.Day
import fr.outadoc.aoc.scaffold.Year

class Day13 : Day(Year.TwentyTwenty) {

    private val input: List<String> =
        readDayInput().lines()

    private val earliestDepartureTime: Long =
        input.first().toLong()

    private val buses: List<Int> =
        input.last()
            .split(',')
            .filterNot { it == "x" }
            .map { it.toInt() }

    private val Int.timeSinceLastDeparture: Long
        get() = earliestDepartureTime % this

    private val Int.timeUntilNextDeparture: Long
        get() = this - timeSinceLastDeparture

    override fun step1(): Long {
        val (nextBus, waitTime) = buses
            .map { bus -> bus to bus.timeUntilNextDeparture }
            .minByOrNull { it.second }!!

        return nextBus * waitTime
    }

    override fun step2(): Long {
        TODO("Not yet implemented")
    }
}