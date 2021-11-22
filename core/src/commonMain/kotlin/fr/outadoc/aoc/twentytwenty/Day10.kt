package fr.outadoc.aoc.twentytwenty

import fr.outadoc.aoc.scaffold.Day
import fr.outadoc.aoc.scaffold.Year
import fr.outadoc.aoc.scaffold.max
import fr.outadoc.aoc.scaffold.min

class Day10 : Day(Year.TwentyTwenty) {

    companion object {
        const val OUTLET_JOLTS = 0L
        const val BUILT_IN_ADAPTER_JOLTS_DIFFERENCE = 3
        val ADAPTER_TOLERANCE: IntRange = 1..3
    }

    private val input: List<Long> =
        readDayInput()
            .lines()
            .map { it.toLong() }
            .sorted()

    private val maxAdapterJolts: Long =
        input.max()

    private val builtInAdapterJolts: Long =
        maxAdapterJolts + BUILT_IN_ADAPTER_JOLTS_DIFFERENCE

    private val adapterList: List<Long> =
        input + builtInAdapterJolts

    private tailrec fun makeAdapterChain(
        remainingAdapters: List<Long>,
        currentChain: List<Long> = listOf(OUTLET_JOLTS)
    ): List<Long> {
        return when {
            remainingAdapters.isEmpty() -> currentChain
            else -> {
                val tail = currentChain.last()
                val nextAdapter: Long = remainingAdapters
                    .filter { it - tail in ADAPTER_TOLERANCE }
                    .min()

                makeAdapterChain(
                    remainingAdapters = remainingAdapters - nextAdapter,
                    currentChain = currentChain + nextAdapter
                )
            }
        }
    }

    private fun List<Long>.countDifferences(n: Long): Int {
        // Compare the difference between each consecutive pair of items in the list
        return windowed(size = 2).count { it[1] - it[0] == n }
    }

    fun step1(): Int {
        return makeAdapterChain(adapterList).run {
            countDifferences(1) * countDifferences(3)
        }
    }

    fun step2(): Long {
        val initial = mapOf(
            OUTLET_JOLTS to 1L
        )

        // For each adapter, count the number of chains that use it.
        val res = adapterList
            .fold(initial) { acc, adapter ->
                acc.toMutableMap()
                    .withDefault { 0L }
                    .apply {
                        // For this adapter, increment the number of chains
                        // for possible *previous* elements in the chain
                        this[adapter] = ADAPTER_TOLERANCE.sumOf { difference ->
                            getValue(adapter - difference)
                        }
                    }
            }

        // Since every *possible* chain ends at the built-in adapter, the maximum
        // value in the map contains the number of possible chains.
        return res.getValue(builtInAdapterJolts)
    }
}