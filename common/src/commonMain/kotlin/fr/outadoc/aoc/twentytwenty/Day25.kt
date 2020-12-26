package fr.outadoc.aoc.twentytwenty

import fr.outadoc.aoc.scaffold.Day
import fr.outadoc.aoc.scaffold.Year

class Day25 : Day(Year.TwentyTwenty) {

    companion object {
        private const val IV = 20201227
    }

    private val input = readDayInput().lines()

    private val cardPublicKey: Long = input[0].toLong()
    private val doorPublicKey: Long = input[1].toLong()

    private fun singleLoop(acc: Long, subject: Long): Long {
        return (acc * subject) % IV
    }

    private fun transform(subject: Long, loopSize: Int): Long {
        return (0 until loopSize).fold(1L) { acc, _ ->
            singleLoop(acc, subject)
        }
    }

    private fun findLoopSizeForPublicKey(subject: Long, publicKey: Long): Int {
        var acc = 1L
        var loopSize = 1
        while (acc != publicKey) {
            acc = singleLoop(acc, subject)
            loopSize++
        }
        return loopSize - 1
    }

    fun step1(): Long {
        val cardLoopSize = findLoopSizeForPublicKey(subject = 7, publicKey = cardPublicKey)
        return transform(subject = doorPublicKey, loopSize = cardLoopSize)
    }
}