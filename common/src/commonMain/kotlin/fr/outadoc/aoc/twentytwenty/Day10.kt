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

    private val adapterList: List<Long> =
        readDayInput()
            .lines()
            .map { it.toLong() }
            .sorted()

    private val highestJoltAdapter: Long =
        adapterList.max()

    private val builtInAdapterJolts: Long =
        highestJoltAdapter + BUILT_IN_ADAPTER_JOLTS_DIFFERENCE

    private tailrec fun makeAdapterChain(
        remainingAdapters: List<Long>,
        currentChain: List<Long> = listOf(OUTLET_JOLTS)
    ): List<Long> {
        return if (remainingAdapters.isEmpty()) {
            currentChain + builtInAdapterJolts
        } else {
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

    private fun List<Long>.countDifferences(n: Long): Int {
        // Compare the difference between each consecutive pair of items in the list
        return windowed(size = 2).count { it[1] - it[0] == n }
    }

    override fun step1(): Long {
        return makeAdapterChain(adapterList).run {
            countDifferences(1) * countDifferences(3)
        }.toLong()
    }

    override fun step2(): Long {
        val initial = mapOf(
            OUTLET_JOLTS to 1L
        )

        val res = adapterList.fold(initial) { acc, adapter ->
            acc.toMutableMap()
                .withDefault { 0L }
                .apply {
                    this[adapter] = ADAPTER_TOLERANCE.sumOf { difference ->
                        getValue(adapter - difference)
                    }
                }
        }

        return res.getValue(highestJoltAdapter).toLong()
    }
}