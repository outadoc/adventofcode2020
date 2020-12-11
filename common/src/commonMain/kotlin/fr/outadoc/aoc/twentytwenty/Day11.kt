package fr.outadoc.aoc.twentytwenty

import fr.outadoc.aoc.scaffold.Day
import fr.outadoc.aoc.scaffold.Year

class Day11 : Day(Year.TwentyTwenty) {

    companion object {
        private const val SEAT_EMPTY = 'L'
        private const val SEAT_OCCUPIED = '#'
        private const val FLOOR = '.'
    }

    private val initialState: Array<CharArray> =
        readDayInput()
            .lines()
            .map { line -> line.toCharArray() }
            .toTypedArray()

    private val height = initialState.size
    private val width = initialState[0].size

    private fun Array<CharArray>.nextState(): Array<CharArray> {
        return this.mapIndexed { iy, line ->
            line.mapIndexed { ix, item ->
                val occupied = countAdjacentSeats(ix, iy)
                when {
                    item == SEAT_EMPTY && occupied == 0 -> SEAT_OCCUPIED
                    item == SEAT_OCCUPIED && occupied >= 4 -> SEAT_EMPTY
                    else -> item
                }
            }.toCharArray()
        }.toTypedArray()
    }

    private fun Array<CharArray>.countAdjacentSeats(x: Int, y: Int): Int {
        var count = 0
        for (iy in (y - 1)..(y + 1)) {
            for (ix in (x - 1)..(x + 1)) {
                if (ix !in 0..width || iy !in 0..height) continue
                if (this[iy][ix] == SEAT_OCCUPIED) {
                    count++
                }
            }
        }
        return count
    }

    override fun step1(): Long {
        TODO("Not yet implemented")
    }

    override fun step2(): Long {
        TODO("Not yet implemented")
    }
}