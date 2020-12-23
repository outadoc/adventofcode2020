package fr.outadoc.aoc.twentytwenty

import fr.outadoc.aoc.scaffold.Day
import fr.outadoc.aoc.scaffold.Year
import fr.outadoc.aoc.scaffold.max
import fr.outadoc.aoc.scaffold.min

class Day23 : Day(Year.TwentyTwenty) {

    private data class State(val cups: List<Int>, val currentCupIndex: Int)

    private val initialState: State =
        readDayInput()
            .lines()
            .first()
            .map { it.toString().toInt() }
            .let { State(cups = it, currentCupIndex = 0) }

    private fun List<Int>.cupAtIndex(index: Int): Int =
        this[index % size]

    private fun State.next(): State {
        val cupStr = cups.joinToString(separator = " ") { cup ->
            if (cup == cups[currentCupIndex]) "($cup)"
            else "$cup"
        }

        println("cups: $cupStr")

        val currentCup = cups.cupAtIndex(currentCupIndex)

        val pickedCups = (currentCupIndex + 1..currentCupIndex + 3)
            .fold(emptyList<Int>()) { acc, index ->
                acc + cups.cupAtIndex(index)
            }

        println("picked up: $pickedCups")

        val nextCups = cups.subList(0, currentCupIndex + 1) +
            cups.subList(currentCupIndex + 4, cups.size)

        println("remaining: $nextCups")

        val destinationCup = ((currentCup - 1) downTo nextCups.min()).firstOrNull { cup ->
            cup in nextCups
        } ?: nextCups.max()

        val destinationIndex = nextCups.indexOf(destinationCup)

        println("destination: $destinationCup (idx $destinationIndex)")

        val final = nextCups.toMutableList()

        pickedCups.forEachIndexed { index, cup ->
            val targetIndex = (destinationIndex + index + 1) % cups.size
            println("inserting $cup at $index")
            final.add(targetIndex, cup)
        }

        println("final: $final")
        println()

        return State(
            cups = final,
            currentCupIndex = (currentCupIndex + 1) % final.size
        )
    }

    private fun State.nthIteration(n: Int): State {
        return (0 until n).fold(this) { state, _ ->
            state.next()
        }
    }

    fun step1(): Long {
        val finalState = initialState.nthIteration(100)
        TODO()
    }

    fun step2(): Long {
        TODO()
    }
}