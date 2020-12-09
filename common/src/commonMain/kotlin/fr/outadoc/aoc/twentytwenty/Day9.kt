package fr.outadoc.aoc.twentytwenty

import fr.outadoc.aoc.scaffold.Day
import fr.outadoc.aoc.scaffold.Year

class Day9 : Day(Year.TwentyTwenty) {

    companion object {
        const val PREAMBLE_LENGTH = 25
    }

    private val input = readDayInput()
        .lineSequence()
        .map { it.toLong() }

    private fun checkSum(chunk: List<Long>): Boolean {
        val n = chunk.first()
        val rest = chunk.drop(1)

        return rest.any { a ->
            (rest - a).any { b ->
                a + b == n
            }
        }
    }

    private fun findContiguousSum(chunk: List<Long>, n: Long): Pair<Long, Long>? {
        var sum = 0L
        var i = 0

        while (sum < n && i < chunk.size) {
            sum += chunk[i]
            i++
        }

        return when (sum) {
            // We've found
            n -> {
                val range = chunk.subList(0, i)
                range.minOrNull()!! to range.maxOrNull()!!
            }
            else -> null
        }
    }

    override fun step1(): Long {
        return input
            .toList()
            // Reverse so that `windowed` takes the previous 25 instead of the next
            .reversed()
            // For every member, consider it and the previous 25 members
            .windowed(size = PREAMBLE_LENGTH + 1, step = 1)
            // Re-reverse so that we start at the beginning
            .reversed()
            .first { chunk -> !checkSum(chunk) }.first()
    }

    override fun step2(): Long {
        val n = 22477624L

        val interval = input
            .filter { it < n }
            .toList()
            .reversed()

        var chunk = interval
        var answer: Pair<Long, Long>? = null

        while (answer == null) {
            answer = findContiguousSum(chunk, n)
            chunk = chunk.drop(1)
        }

        return answer.first + answer.second
    }
}