package fr.outadoc.aoc.twentytwenty

import fr.outadoc.aoc.scaffold.Day
import fr.outadoc.aoc.scaffold.Year
import fr.outadoc.aoc.scaffold.max
import kotlin.time.ExperimentalTime

@ExperimentalTime
class Day23 : Day(Year.TwentyTwenty) {

    /**
     * @param cups map of cup value to next cup value in the cyclic list
     */
    private data class State(val cups: MutableMap<Int, Int>, val currentCup: Int) {
        val range: IntRange = 1..cups.size
    }

    private val input: List<Int> =
        readDayInput()
            .lines()
            .first()
            .map { it.toString().toInt() }

    private fun List<Int>.toQuickMap(): Map<Int, Int> =
        mapIndexed { index, cup ->
            val next = if (index == size - 1) this[0] else this[index + 1]
            cup to next
        }.toMap()

    private val step1State = State(
        cups = input.toQuickMap().toMutableMap(),
        currentCup = input.first()
    )

    private val step2State = State(
        cups = (input + (input.max() + 1..1_000_000)).toQuickMap().toMutableMap(),
        currentCup = input.first()
    )

    private fun State.next(): State {
        // Pick up 3 cups after the current cup
        val c1 = cups.getValue(currentCup)
        val c2 = cups.getValue(c1)
        val c3 = cups.getValue(c2)

        val rangeForCupLowerThanCurrent = (currentCup - 1) downTo range.first
        val rangeForHighestCup = range.last downTo range.last - 3

        // Select the destination cup
        val destinationCup: Int =
            rangeForCupLowerThanCurrent.firstOrNull { cup -> cup != c1 && cup != c2 && cup != c3 }
                ?: rangeForHighestCup.first { cup -> cup != c1 && cup != c2 && cup != c3 }

        // Move cups to the right position by inserting c1, c2, c3 between destinationCup and its next
        cups.getValue(destinationCup).let { oldDestinationNextCup ->
            cups[currentCup] = cups.getValue(c3)
            cups[destinationCup] = c1
            cups[c3] = oldDestinationNextCup
        }

        return copy(currentCup = cups.getValue(currentCup))
    }

    private fun State.nthIteration(n: Int): State {
        return (0 until n).foldIndexed(this) { index, state, _ ->
            if (index % 100 == 0) {
                val progress = index.toFloat() / n.toFloat() * 100f
                println("$progress %")
            }

            // println(state.cups.toList(1))
            state.next()
        }
    }

    private fun Map<Int, Int>.toList(startingCup: Int): List<Int> {
        var cup: Int = getValue(startingCup)
        val list = mutableListOf<Int>()
        do {
            list.add(cup)
            cup = getValue(cup)
        } while (cup != startingCup)
        return list
    }

    private fun State.toStateString(): String {
        return cups.toList(startingCup = 1).joinToString(separator = "")
    }

    fun step1(): Long {
        return step1State
            .nthIteration(100)
            .toStateString()
            .toLong()
    }

    fun step2(): Long {
        return step2State
            .nthIteration(10_000_000)
            .cups.toList(startingCup = 1)
            .take(2)
            .fold(1L) { acc, cup -> acc * cup.toLong() }
    }
}