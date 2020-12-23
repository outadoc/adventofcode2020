package fr.outadoc.aoc.twentytwenty

import fr.outadoc.aoc.scaffold.Day
import fr.outadoc.aoc.scaffold.Year
import fr.outadoc.aoc.scaffold.max
import fr.outadoc.aoc.scaffold.min

class Day23 : Day(Year.TwentyTwenty) {

    companion object {
        private const val PRINT_DEBUG = false
    }

    private data class State(val cups: List<Int>)

    private val initialState: State =
        readDayInput()
            .lines()
            .first()
            .map { it.toString().toInt() }
            .let { State(cups = it) }

    private fun State.next(): State {
        // Current cup is always the first one
        val currentCup = cups.first()

        // Pick up 3 cups after the current cup
        val pickedCups = cups.drop(1).take(3)

        // What remains of the cups without the ones we picked up
        val remainingCups = listOf(currentCup) + cups.drop(4)

        // Select the destination cup
        val destinationCup = ((currentCup - 1) downTo remainingCups.min())
            .firstOrNull { cup -> cup in remainingCups } ?: remainingCups.max()

        // Move cups to the right position
        val withPickedCups: List<Int> =
            remainingCups.takeWhile { it != destinationCup } +
                destinationCup +
                pickedCups +
                remainingCups.takeLastWhile { it != destinationCup }

        // Place the current cup at the back of the list
        val final = withPickedCups.drop(1) + withPickedCups.first()

        if (PRINT_DEBUG) {
            val cupStr = cups.joinToString(separator = " ") { cup ->
                if (cup == currentCup) "($cup)"
                else "$cup"
            }

            println("cups: $cupStr")
            println("picked up: $pickedCups")
            println("remaining: $remainingCups")
            println("destination: $destinationCup")
            println("final: $final")
            println()
        }

        return State(cups = final)
    }

    private fun State.nthIteration(n: Int): State {
        return (0 until n).fold(this) { state, _ ->
            state.next()
        }
    }

    private fun State.toStateString(): String {
        val reordered = cups.takeLastWhile { it != 1 } + cups.takeWhile { it != 1 }
        return reordered.joinToString(separator = "")
    }

    fun step1(): Long {
        return initialState
            .nthIteration(100)
            .toStateString()
            .toLong()
    }

    fun step2(): Long {
        val bigCrabBigStakes = initialState.copy(
            cups = initialState.cups + (initialState.cups.max() until 1_000_000)
        )

        val finalState = bigCrabBigStakes.nthIteration(10_000_000)
        val indexOfCup1 = finalState.cups.indexOf(1)
        return finalState.cups[indexOfCup1 + 1].toLong() * finalState.cups[indexOfCup1 + 2].toLong()
    }
}