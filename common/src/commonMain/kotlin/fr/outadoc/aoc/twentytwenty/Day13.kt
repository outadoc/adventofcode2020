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
            .map { bus ->
                when (bus) {
                    "x" -> -1
                    else -> bus.toInt()
                }
            }

    private val validBuses: List<Int> =
        buses.filterNot { it < 0 }

    private fun Int.getTimeUntilNextDeparture(timestamp: Long): Long =
        this - (timestamp % this)

    override fun step1(): Long {
        val (nextBus, waitTime) = validBuses
            .map { bus -> bus to bus.getTimeUntilNextDeparture(earliestDepartureTime) }
            .minByOrNull { it.second }!!

        return nextBus * waitTime
    }

    override fun step2(): Long {
        TODO("Not yet implemented")
    }
}