package fr.outadoc.aoc.twentytwentyone

import fr.outadoc.aoc.scaffold.Day
import fr.outadoc.aoc.scaffold.permutations
import fr.outadoc.aoc.scaffold.readDayInput

class Day08 : Day<Int> {

    private companion object {

        const val a = 'a'
        const val b = 'b'
        const val c = 'c'
        const val d = 'd'
        const val e = 'e'
        const val f = 'f'
        const val g = 'g'

        val segments = (a..g).toList()

        val digits = arrayOf(
            setOf(a, b, c, e, f, g),    // 0
            setOf(c, f),                // 1
            setOf(a, c, d, e, g),       // 2
            setOf(a, c, d, f, g),       // 3
            setOf(b, c, d, f),          // 4
            setOf(a, b, d, f, g),       // 5
            setOf(a, b, d, e, f, g),    // 6
            setOf(a, c, f),             // 7
            setOf(a, b, c, d, e, f, g), // 8
            setOf(a, b, c, d, f, g)     // 9
        )
    }

    private data class Configuration(
        val signals: List<Set<Char>>,
        val output: List<Set<Char>>
    )

    private val input = readDayInput()
        .lineSequence()
        .map { line ->
            val (signals, output) = line.split(" | ")
            Configuration(
                signals = signals
                    .split(' ')
                    .map { signal -> signal.toSet() },
                output = output
                    .split(' ')
                    .map { signal -> signal.toSet() }
            )
        }

    override fun step1() = input.sumOf { state ->
        state.output.count { signal ->
            digits.count { segments -> segments.size == signal.size } == 1
        }
    }

    override fun step2(): Int {
        val possibleMappings: List<Map<Char, Char>> =
            segments
                .permutations
                .map { permut ->
                    permut.mapIndexed { index, seg ->
                        segments[index] to seg
                    }.toMap()
                }

        return input.sumOf { config ->
            val digitSet = digits.toSet()
            val correctMapping = possibleMappings
                .filter { mapping ->
                    // Check that the digits of unique segment length match
                    config.signals.first { it.size == digits[1].size }.translate(mapping) == digits[1]
                        && config.signals.first { it.size == digits[4].size }.translate(mapping) == digits[4]
                        && config.signals.first { it.size == digits[7].size }.translate(mapping) == digits[7]
                        && config.signals.first { it.size == digits[8].size }.translate(mapping) == digits[8]
                }
                .first { mapping ->
                    // Check that all digits can be mapped
                    val signals = config.signals.map { signal ->
                        signal.translate(mapping)
                    }.toSet()

                    digitSet == signals
                }

            val decodedOutput = config.output.map { output ->
                val translated = output.translate(correctMapping)
                val digit = digits.indexOf(translated)
                digit
            }

            decodedOutput.joinToString("").toInt()
        }
    }

    private fun Set<Char>.translate(mapping: Map<Char, Char>): Set<Char> =
        map { seg -> mapping.getValue(seg) }.toSet()

    override val expectedStep1 = 375
    override val expectedStep2 = 1_019_355
}
