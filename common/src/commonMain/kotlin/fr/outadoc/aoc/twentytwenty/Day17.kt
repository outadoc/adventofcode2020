package fr.outadoc.aoc.twentytwenty

import fr.outadoc.aoc.scaffold.Day
import fr.outadoc.aoc.scaffold.Year

class Day17 : Day(Year.TwentyTwenty) {

    companion object {
        const val C_EMPTY = '.'
    }

    data class Slice(val xOrigin: Int, val yOrigin: Int, val slice: List<List<Char>>) {

        operator fun get(x: Int, y: Int): Char? {
            return slice.getOrNull(yOrigin + y)?.getOrNull(xOrigin + x)
        }

        fun grow(n: Int = 1): Slice {
            val newSliceSize = (0..(slice.size + 2 * n))
            val emptyColumn: List<Char> = newSliceSize.map { C_EMPTY }
            return copy(
                xOrigin = xOrigin + n,
                yOrigin = yOrigin + n,
                slice = listOf(emptyColumn) + slice + listOf(emptyColumn)
            )
        }
    }

    data class Dimension(val zOrigin: Int, val slices: List<Slice>) {

        val size = slices.size

        operator fun get(x: Int, y: Int, z: Int): Char {
            return slices.getOrNull(zOrigin + z)?.get(x, y) ?: C_EMPTY
        }

        fun grow(n: Int = 1): Dimension {
            return copy(
                zOrigin = zOrigin + n,
                slices = slices.map { slice -> slice.grow(n) }
            )
        }
    }

    override fun step1(): Long {
        TODO("Not yet implemented")
    }

    override fun step2(): Long {
        TODO("Not yet implemented")
    }
}