package fr.outadoc.aoc.twentytwenty

import fr.outadoc.aoc.scaffold.Day
import fr.outadoc.aoc.scaffold.Year

class Day17 : Day(Year.TwentyTwenty) {

    companion object {
        const val C_EMPTY = '.'
    }

    data class Slice(val rows: List<List<Char>>) {

        operator fun get(x: Int, y: Int): Char? {
            return rows.getOrNull(y)?.getOrNull(x)
        }

        fun grow(n: Int = 1): Slice {
            val newSliceSize = (0..(rows.size + 2 * n))
            val emptyRow: List<Char> = newSliceSize.map { C_EMPTY }
            return copy(
                rows = listOf(emptyRow) + rows + listOf(emptyRow)
            )
        }
    }

    data class Dimension(val xOrigin: Int, val yOrigin: Int, val zOrigin: Int, val slices: List<Slice>) {

        val size = slices.size

        operator fun get(x: Int, y: Int, z: Int): Char {
            return slices.getOrNull(zOrigin + z)?.get(xOrigin + x, yOrigin + y) ?: C_EMPTY
        }

        fun grow(n: Int = 1): Dimension {
            return copy(
                zOrigin = zOrigin + n,
                slices = slices.map { slice -> slice.grow(n) }
            )
        }
    }

    fun Dimension.next(): Dimension {
        return this
    }

    fun print(dimension: Dimension) {
        dimension.slices.forEachIndexed { index, slice ->
            println("z = ${index + dimension.zOrigin}")
            slice.rows.forEach { row ->
                println(row.joinToString())
            }
            println("\n")
        }
    }

    override fun step1(): Long {
        TODO("Not yet implemented")
    }

    override fun step2(): Long {
        TODO("Not yet implemented")
    }
}