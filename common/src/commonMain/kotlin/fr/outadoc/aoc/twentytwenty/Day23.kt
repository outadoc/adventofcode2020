package fr.outadoc.aoc.twentytwenty

import fr.outadoc.aoc.scaffold.Day
import fr.outadoc.aoc.scaffold.Year
import fr.outadoc.aoc.scaffold.max
import fr.outadoc.aoc.scaffold.measure
import kotlin.time.ExperimentalTime

@ExperimentalTime
class Day23 : Day(Year.TwentyTwenty) {

    companion object {
        private const val PRINT_DEBUG = false
    }

    private data class State(val cups: ArrayDeque<Int>) {
        val range: IntRange = 1..cups.size
    }

    private val initialState: State =
        readDayInput()
            .lines()
            .first()
            .map { it.toString().toInt() }
            .let { State(cups = ArrayDeque(it)) }

    private fun State.next(): State {
        return measure("COMPLETE") {
            // Current cup is always the first one
            val currentCup = cups[0]

            if (PRINT_DEBUG) {
                val cupStr = cups.joinToString(separator = " ") { cup ->
                    if (cup == currentCup) "($cup)"
                    else "$cup"
                }

                println("cups: $cupStr")
            }

            // Pick up 3 cups after the current cup
            val pickedCups = cups.slice(1..3)

            repeat(3) {
                cups.removeAt(1)
            }

            val maxRemainingCup = measure("REMAINING_CUP") {
                // What remains of the cups without the ones we picked up
                (range.last downTo range.last - 3).first { cup ->
                    cup !in pickedCups
                }
            }

            // Select the destination cup
            val destinationCup = measure("DESTINATION_CUP") {
                ((currentCup - 1) downTo range.first)
                    .firstOrNull { cup -> cup !in pickedCups } ?: maxRemainingCup
            }

            val destinationCupIndex = measure("LOOKUP") {
                cups.indexOf(destinationCup)
            }

            measure("MOVE") {
                // Move cups to the right position
                cups.addAll(destinationCupIndex + 1, pickedCups)

                // Place the current cup at the back of the list
                cups.removeFirst()
                cups.addLast(currentCup)
            }

            if (PRINT_DEBUG) {
                println("picked up: $pickedCups")
                println("destination: $destinationCup")
                println("final: $cups")
                println()
            }

            measure("FINISH") {
                State(cups = cups)
            }
        }
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