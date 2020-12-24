package fr.outadoc.aoc.twentytwenty

import fr.outadoc.aoc.scaffold.Day
import fr.outadoc.aoc.scaffold.Year
import fr.outadoc.aoc.scaffold.max

class Day23 : Day(Year.TwentyTwenty) {

    companion object {
        private const val PRINT_DEBUG = false
    }

    private data class State(val cups: ArrayDeque<Int>) {
        val lookup: Map<Int, Int> = cups.zip(cups.indices).toMap()
    }

    private val initialState: State =
        readDayInput()
            .lines()
            .first()
            .map { it.toString().toInt() }
            .let { State(cups = ArrayDeque(it)) }

    private fun State.next(): State {
        val deque = ArrayDeque(cups)

        // Current cup is always the first one
        val currentCup = deque[0]

        // Pick up 3 cups after the current cup
        val pickedCups = deque.slice(1..3)

        repeat(3) {
            deque.removeAt(1)
        }

        // What remains of the cups without the ones we picked up
        val maxRemainingCup = (cups.size downTo cups.size - 3).first { cup ->
            cup !in pickedCups
        }

        // Select the destination cup
        val destinationCup = ((currentCup - 1) downTo 1)
            .firstOrNull { cup -> cup !in pickedCups } ?: maxRemainingCup

        val destinationCupIndex = lookup.getValue(destinationCup) - 3

        // Move cups to the right position
        deque.addAll(destinationCupIndex + 1, pickedCups)

        // Place the current cup at the back of the list
        deque.removeFirst()
        deque.addLast(currentCup)

        if (PRINT_DEBUG) {
            val cupStr = cups.joinToString(separator = " ") { cup ->
                if (cup == currentCup) "($cup)"
                else "$cup"
            }

            println("cups: $cupStr")
            println("picked up: $pickedCups")
            println("destination: $destinationCup")
            println("final: $deque")
            println()
        }

        return State(cups = deque)
    }

    private fun State.nthIteration(n: Int): State {
        return (0 until n).foldIndexed(this) { index, state, _ ->
            val progress = index.toFloat() / n.toFloat() * 100f
            println("$progress %")
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
            cups = ArrayDeque(initialState.cups + (initialState.cups.max() until 1_000_000))
        )

        val finalState = bigCrabBigStakes.nthIteration(10_000_000)
        val indexOfCup1 = finalState.cups.indexOf(1)
        return finalState.cups[indexOfCup1 + 1].toLong() * finalState.cups[indexOfCup1 + 2].toLong()
    }
}