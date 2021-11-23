package fr.outadoc.aoc.twentytwenty

import fr.outadoc.aoc.scaffold.Day
import fr.outadoc.aoc.scaffold.readDayInput

class Day13 : Day<Long> {

    private val input: List<String> = readDayInput().lines()

    private val earliestDepartureTime: Long =
        input.first().toLong()

    private fun Bus.getTimeUntilNextDeparture(timestamp: Long) =
        when (val mod = timestamp % id) {
            0L -> 0
            else -> (id - mod).toInt()
        }

    private data class Bus(val id: Long, val index: Int)

    private val buses: List<Bus> =
        input.last()
            .split(',')
            .map { bus ->
                when (bus) {
                    "x" -> -1L
                    else -> bus.toLong()
                }
            }
            .mapIndexed { index, bus -> Bus(bus, index) }
            .filterNot { bus -> bus.id < 0 }

    private fun Bus.doesBusPassAtTimestamp(timestamp: Long): Boolean =
        (timestamp + index) % id == 0L

    override fun step1(): Long {
        val (nextBus, waitTime) = buses
            .map { bus -> bus to bus.getTimeUntilNextDeparture(earliestDepartureTime) }
            .minByOrNull { it.second }!!

        return nextBus.id * waitTime
    }

    override fun step2(): Long {
        val head = buses.first()
        return buses
            .drop(1)
            .fold(head.id to head.id) { (t, step), bus ->
                var t1 = t
                while (!bus.doesBusPassAtTimestamp(t1)) {
                    t1 += step
                }
                t1 to step * bus.id
            }
            .first
    }

    override val expectedStep1: Long = 1895
    override val expectedStep2: Long = 840493039281088
}