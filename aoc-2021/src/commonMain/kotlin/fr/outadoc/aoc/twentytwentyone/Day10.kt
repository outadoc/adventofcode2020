package fr.outadoc.aoc.twentytwentyone

import fr.outadoc.aoc.scaffold.Day
import fr.outadoc.aoc.scaffold.median
import fr.outadoc.aoc.scaffold.readDayInput

class Day10 : Day<Long> {

    private val input = readDayInput()
        .lineSequence()
        .map { line -> line.toList() }

    private class WrongClosingCharacterException(val character: Char) : Exception()

    private companion object {
        val chars = mapOf(
            '(' to ')',
            '[' to ']',
            '{' to '}',
            '<' to '>'
        )
    }

    @Throws(WrongClosingCharacterException::class)
    private fun List<Char>.sanitize(): List<Char> {
        val leftOpen = fold(emptyList<Char>()) { acc, c ->
            when {
                c.isOpening -> acc + c
                c.isClosing -> {
                    if (acc.lastOrNull() != c.matchingOpening) {
                        throw WrongClosingCharacterException(c)
                    }

                    acc.dropLast(1)
                }
                else -> error("illegal character")
            }
        }

        val missing = leftOpen.foldRight(emptyList<Char>()) { c, acc ->
            acc + c.matchingClosing
        }

        println("$this was missing $missing")

        return this + missing
    }

    private val Char.isOpening: Boolean
        get() = this in chars.keys

    private val Char.isClosing: Boolean
        get() = this in chars.values

    private val Char.matchingOpening: Char
        get() = chars.entries.first { (_, closing) -> closing == this }.key

    private val Char.matchingClosing: Char
        get() = chars.getValue(this)

    private val Char.corruptionScore: Long
        get() = when (this) {
            ')' -> 3
            ']' -> 57
            '}' -> 1197
            '>' -> 25137
            else -> error("illegal character")
        }

    private val Char.completionScore: Long
        get() = when (this) {
            ')' -> 1
            ']' -> 2
            '}' -> 3
            '>' -> 4
            else -> error("illegal character")
        }

    private val List<Char>.completionScore: Long
        get() = fold(0) { acc, c ->
            acc * 5 + c.completionScore
        }

    override fun step1() = input.sumOf { line ->
        try {
            line.sanitize()
            0L
        } catch (e: WrongClosingCharacterException) {
            e.character.corruptionScore
        }
    }

    override fun step2() = input
        .mapNotNull { line ->
            try {
                val sanitized = line.sanitize()
                val wasMissing = sanitized.takeLast(sanitized.size - line.size)
                wasMissing.completionScore
            } catch (e: WrongClosingCharacterException) {
                null
            }
        }
        .toList()
        .sorted()
        .median()

    override val expectedStep1 = 319_233L
    override val expectedStep2 = 1_118_976_874L
}
