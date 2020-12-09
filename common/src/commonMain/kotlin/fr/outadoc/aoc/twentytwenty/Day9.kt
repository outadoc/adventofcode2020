package fr.outadoc.aoc.twentytwenty

import fr.outadoc.aoc.scaffold.Day
import fr.outadoc.aoc.scaffold.Year

class Day9 : Day(Year.TwentyTwenty) {

    companion object {
        const val PREAMBLE_LENGTH = 25
    }

    private val chunks = readDayInput()
        .lineSequence()
        .map { it.toLong() }
        .toList()
        // Reverse so that `windowed` takes the previous 25 instead of the next
        .reversed()
        // For every member, consider it and the previous 25 members
        .windowed(size = PREAMBLE_LENGTH + 1, step = 1)
        // Re-reverse so that we start at the beginning
        .reversed()

    private fun checkSum(chunk: List<Long>): Boolean {
        val n = chunk.first()
        val rest = chunk.drop(1)

        return rest.any { a ->
            (rest - a).any { b ->
                (a + b == n).apply {
                    if (this) {
                        println("$n = $a + $b")
                    }
                }
            }
        }
    }

    override fun step1(): Long {
        return chunks.first { chunk -> !checkSum(chunk) }.first()
    }

    override fun step2(): Long {
        TODO("Not yet implemented")
    }
}