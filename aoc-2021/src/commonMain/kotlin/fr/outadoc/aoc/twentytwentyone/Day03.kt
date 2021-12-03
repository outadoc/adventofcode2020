package fr.outadoc.aoc.twentytwentyone

import fr.outadoc.aoc.scaffold.Day
import fr.outadoc.aoc.scaffold.readDayInput

@Suppress("NonAsciiCharacters", "LocalVariableName")
class Day03 : Day<Int> {

    private val input: List<IntArray> = readDayInput()
        .lineSequence()
        .map { line -> line.map { it.digitToInt() }.toIntArray() }
        .toList()

    private fun IntArray.flip() = map { if (it == 1) 0 else if (it == 0) 1 else it }.toIntArray()
    private fun IntArray.toInt() = joinToString("").toInt(radix = 2)

    private fun List<IntArray>.mostCommonBitMap(): IntArray {
        return reduce { sum, line -> sum.zip(line) { a, b -> a + b }.toIntArray() }
            .map { oneCount ->
                val zeroCount = size - oneCount
                when {
                    oneCount > zeroCount -> 1
                    oneCount < zeroCount -> 0
                    else -> -1
                }
            }
            .toIntArray()
    }

    override fun step1(): Int {
        val mostCommonBitMap = input.mostCommonBitMap()
        val γ = mostCommonBitMap.toInt()
        val ε = mostCommonBitMap.flip().toInt()
        return γ * ε
    }

    override fun step2(): Int {
        val oxygenGeneratorRating = filterWithBitCriteria(candidates = input, wantedBit = 1).toInt()
        val co2ScrubberRating = filterWithBitCriteria(candidates = input, wantedBit = 0).toInt()
        return oxygenGeneratorRating * co2ScrubberRating
    }

    private fun filterWithBitCriteria(candidates: List<IntArray>, wantedBit: Int, index: Int = 0): IntArray {
        return if (candidates.size == 1) candidates.first()
        else {
            val mostCommonBitMap = candidates.mostCommonBitMap().let { if (wantedBit == 0) it.flip() else it }
            filterWithBitCriteria(
                candidates = candidates.filter { candidate ->
                    candidate[index] == mostCommonBitMap[index]
                        || (mostCommonBitMap[index] == -1 && candidate[index] == wantedBit)
                },
                wantedBit = wantedBit,
                index = index + 1
            )
        }
    }

    override val expectedStep1 = 3882564
    override val expectedStep2 = 3385170
}
