package fr.outadoc.aoc.twentytwenty

import fr.outadoc.aoc.scaffold.Day
import fr.outadoc.aoc.scaffold.Year
import fr.outadoc.aoc.scaffold.max
import fr.outadoc.aoc.scaffold.min

class Day23 : Day(Year.TwentyTwenty) {

    private data class State(val cups: List<Int>)

    private val initialState: State =
        readDayInput()
            .lines()
            .first()
            .map { it.toString().toInt() }
            .let { State(cups = it) }

    private fun List<Int>.cupAtIndex(index: Int): Int =
        this[index % size]

    private fun State.next(): State {
        // Current cup is always the first one
        val currentCup = cups.first()

        val cupStr = cups.joinToString(separator = " ") { cup ->
            if (cup == currentCup) "($cup)"
            else "$cup"
        }

        println("cups: $cupStr")

        // Pick up 3 cups after the current cup
        val pickedCups = cups.drop(1).take(3)

        println("picked up: $pickedCups")

        val remainingCups = listOf(currentCup) + cups.drop(4)

        println("remaining: $remainingCups")

        // Select the destination cup
        val destinationCup = ((currentCup - 1) downTo remainingCups.min())
            .firstOrNull { cup -> cup in remainingCups } ?: remainingCups.max()

        println("destination: $destinationCup")

        // Move cups to the right position
        val withPickedCups: List<Int> =
            remainingCups.takeWhile { it != destinationCup } +
                destinationCup +
                pickedCups +
                remainingCups.takeLastWhile { it != destinationCup }

        // Place the current cup at the back of the list
        val final = withPickedCups.drop(1) + withPickedCups.first()

        println("final: $final")
        println()

        return State(cups = final)
    }

    private fun State.nthIteration(n: Int): State {
        return (0 until n).fold(this) { state, _ ->
            state.next()
        }
    }

    private fun State.toStateString(): String {
        val reordered = cups.takeWhile { it != 1 } + cups.takeLastWhile { it != 1 }
        return reordered.joinToString(separator = "")
    }

    fun step1(): String {
        return initialState
            .nthIteration(100)
            .toStateString()
    }

    fun step2(): Long {
        TODO()
    }
}