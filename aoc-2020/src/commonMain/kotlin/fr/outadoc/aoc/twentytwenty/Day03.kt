package fr.outadoc.aoc.twentytwenty

import fr.outadoc.aoc.scaffold.Day
import fr.outadoc.aoc.scaffold.Year

class Day03 : Day(Year.TwentyTwenty) {

    private val map: Array<CharArray> =
        readDayInput()
        .lines()
        .map { line -> line.toCharArray() }
        .toTypedArray()

    private val Array<CharArray>.height: Int
        get() = size

    private val Array<CharArray>.width: Int
        get() = this[0].size

    companion object {
        private const val TREE = '#'
    }

    private fun Array<CharArray>.getPoint(x: Int, y: Int): Char {
        return this[y][x % width]
    }

    private fun Array<CharArray>.getNumberOfTreesOnSlope(slope: Pair<Int, Int>): Long {
        var ix = 0
        var iy = 0
        var treeCount = 0L

        while (iy < height) {
            if (getPoint(ix, iy) == TREE) {
                treeCount += 1
            }
            ix += slope.first
            iy += slope.second
        }

        return treeCount
    }

    fun step1(): Long {
        val slope = 3 to 1
        return map.getNumberOfTreesOnSlope(slope)
    }

    fun step2(): Long {
        val slopes = listOf(
            1 to 1,
            3 to 1,
            5 to 1,
            7 to 1,
            1 to 2
        )

        return slopes.fold(1) { acc, slope ->
            acc * map.getNumberOfTreesOnSlope(slope)
        }
    }
}