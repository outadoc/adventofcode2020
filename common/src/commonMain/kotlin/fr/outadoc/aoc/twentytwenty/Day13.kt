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

    private fun Int.getTimeUntilNextDeparture(timestamp: Long): Int {
        return when (val mod = timestamp % this) {
            0L -> 0
            else -> (this - mod).toInt()
        }
    }

    private val indexedBuses: List<Pair<Int, Int>> =
        buses.mapIndexed { index, bus -> bus to index }
            .filterNot { (bus, _) -> bus < 0 }
            .sortedByDescending { (bus, _) -> bus }

    private fun areBusesAlignedAtTime(timestamp: Long): Boolean {
        return indexedBuses.all { (bus, index) ->
            (timestamp + index) % bus == 0L
        }
    }

    override fun step1(): Long {
        val (nextBus, waitTime) = validBuses
            .map { bus -> bus to bus.getTimeUntilNextDeparture(earliestDepartureTime) }
            .minByOrNull { it.second }!!

        return (nextBus * waitTime).toLong()
    }

    override fun step2(): Long {
        return (100000000000000..Long.MAX_VALUE).first { timestamp ->
            areBusesAlignedAtTime(timestamp)
        }
    }
}