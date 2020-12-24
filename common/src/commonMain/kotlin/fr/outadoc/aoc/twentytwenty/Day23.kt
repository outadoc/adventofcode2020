package fr.outadoc.aoc.twentytwenty

import fr.outadoc.aoc.scaffold.Day
import fr.outadoc.aoc.scaffold.Year
import fr.outadoc.aoc.scaffold.max

class Day23 : Day(Year.TwentyTwenty) {

    companion object {
        private const val PRINT_DEBUG = false
    }

    private data class State(val cups: List<Int>) {
        val lookup: Map<Int, Int> = cups.zip(cups.indices).toMap()
    }

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
        val pickedCups = cups.subList(1, 4)

        // What remains of the cups without the ones we picked up
        val remainingCups = listOf(currentCup) + cups.drop(4)
        val maxRemainingCup = (cups.size downTo cups.size - 3).first { cup ->
            cup !in pickedCups
        }

        // Select the destination cup
        val destinationCup = ((currentCup - 1) downTo 1)
            .firstOrNull { cup -> cup !in pickedCups } ?: maxRemainingCup

        val destinationCupIndex = lookup.getValue(destinationCup) - 3

        // Move cups to the right position
        val withPickedCups: List<Int> =
            remainingCups.subList(0, destinationCupIndex + 1) +
                pickedCups +
                remainingCups.subList(destinationCupIndex + 1, remainingCups.size)

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
        return (0 until n).foldIndexed(this) { index, state, _ ->
            println("${index.toFloat() / n.toFloat() * 100f} %")
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