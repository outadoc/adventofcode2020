package fr.outadoc.aoc.twentytwenty

import fr.outadoc.aoc.scaffold.Day
import fr.outadoc.aoc.scaffold.Year

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

    private val buildInAdapterJolts = input.maxOrNull()!! + BUILT_IN_ADAPTER_JOLTS_DIFFERENCE

    private val adapterList: List<Long> =
        input

    private tailrec fun makeAdapterChain(
        remainingAdapters: List<Long>,
        currentChain: List<Long> = listOf(OUTLET_JOLTS)
    ): List<Long> {
        return if (remainingAdapters.isEmpty()) {
            currentChain + buildInAdapterJolts
        } else {
            val tail = currentChain.last()
            val nextAdapter: Long = remainingAdapters
                .filter { it - tail in ADAPTER_TOLERANCE }
                .minOrNull()!!

            makeAdapterChain(
                remainingAdapters = remainingAdapters - nextAdapter,
                currentChain = currentChain + nextAdapter
            )
        }
    }

    private fun countPossibleChains(
        remainingAdapters: List<Long>,
        currentChain: List<Long> = listOf(OUTLET_JOLTS)
    ): Int {
        val tail = currentChain.last()

        //println("checking $tail")

        if (buildInAdapterJolts - tail in ADAPTER_TOLERANCE) {
            // This is a good chain
            //println("good chain: $currentChain")
            return 1
        }

        return remainingAdapters
            .filter { it - tail in ADAPTER_TOLERANCE }
            .sumBy { nextAdapterCandidate ->
                countPossibleChains(
                    remainingAdapters = remainingAdapters - nextAdapterCandidate,
                    currentChain = currentChain + nextAdapterCandidate
                )
            }
    }

    private fun List<Long>.countDifferences(n: Long): Long {
        return windowed(size = 2)
            .count { it[1] - it[0] == n }
            .toLong()
    }

    override fun step1(): Long {
        val chain = makeAdapterChain(adapterList)
        return chain.countDifferences(1) * chain.countDifferences(3)
    }

    override fun step2(): Long {
        return countPossibleChains(adapterList).toLong()
    }
}