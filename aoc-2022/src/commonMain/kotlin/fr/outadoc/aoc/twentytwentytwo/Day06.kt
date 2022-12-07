package fr.outadoc.aoc.twentytwentytwo

import fr.outadoc.aoc.scaffold.Day
import fr.outadoc.aoc.scaffold.readDayInput

class Day06 : Day<Int> {

    private val input: List<Char> =
        readDayInput().map { it }

    private fun detectStartMarker(count: Int): Int {
        return input
            .withIndex()
            .windowed(size = count)
            .first { items: List<IndexedValue<Char>> ->
                // Check if all items are identical
                items.map { it.value }.toSet().size == count
            }
            .last()
            .index + 1
    }

    override fun step1(): Int = detectStartMarker(count = 4)
    override fun step2(): Int = detectStartMarker(count = 14)

    override val expectedStep1 = 1_855
    override val expectedStep2 = 3_256
}
