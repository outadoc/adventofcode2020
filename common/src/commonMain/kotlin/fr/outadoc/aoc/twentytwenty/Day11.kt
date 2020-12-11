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

    private fun Array<CharArray>.nextState(adjacentSeatsLookup: (Array<CharArray>, Int, Int) -> Int): Array<CharArray> {
        return this.mapIndexed { iy, line ->
            line.mapIndexed { ix, item ->
                val occupied = adjacentSeatsLookup(this, ix, iy)
                when {
                    item == SEAT_EMPTY && occupied == 0 -> SEAT_OCCUPIED
                    item == SEAT_OCCUPIED && occupied >= 4 -> SEAT_EMPTY
                    else -> item
                }
            }.toCharArray()
        }.toTypedArray()
    }

    private fun countImmediatelyAdjacentSeats(grid: Array<CharArray>, x: Int, y: Int): Int {
        var count = 0
        for (iy in (y - 1)..(y + 1)) {
            for (ix in (x - 1)..(x + 1)) {
                val widthOufOfBounds = ix !in 0 until width
                val heightOufOfBounds = iy !in 0 until height
                val isCurrentSeat = ix == x && iy == y

                if (!(widthOufOfBounds || heightOufOfBounds || isCurrentSeat) && grid[iy][ix] == SEAT_OCCUPIED) {
                    count++
                }
            }
        }
        return count
    }

    private fun print(grid: Array<CharArray>) {
        grid.forEach { line ->
            line.forEach { char ->
                print("$char ")
            }
            println()
        }

        println("---------\n")
    }

    private fun Array<CharArray>.countOccupiedSeats(): Long {
        return sumOf { line ->
            line.count { char ->
                char == SEAT_OCCUPIED
            }
        }.toLong()
    }

    private tailrec fun Array<CharArray>.findFinalState(adjacentSeatsLookup: (Array<CharArray>, Int, Int) -> Int): Array<CharArray> {
        val nextState = this.nextState(adjacentSeatsLookup)
        return when {
            this.contentDeepEquals(nextState) -> nextState
            else -> nextState.findFinalState(adjacentSeatsLookup)
        }
    }

    override fun step1(): Long {
        return initialState
            .findFinalState(::countImmediatelyAdjacentSeats)
            .countOccupiedSeats()
    }

    override fun step2(): Long {
        TODO("Not yet implemented")
    }
}