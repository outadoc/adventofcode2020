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

    data class Vector(val dx: Int, val dy: Int)

    private val possibleDirections: List<Vector>
        get() {
            val vals = (-1..+1)
            return vals.map { dx ->
                vals.map { dy ->
                    Vector(dx, dy)
                }
            }.flatten() - Vector(0, 0)
        }

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

    private fun countImmediatelyAdjacentOccupiedSeats(grid: Array<CharArray>, x: Int, y: Int): Int {
        val verticalRange = (y - 1)..(y + 1)
        val horizontalRange = (x - 1)..(x + 1)

        return verticalRange.sumBy { iy ->
            horizontalRange.count { ix ->
                val widthOufOfBounds = ix !in 0 until width
                val heightOufOfBounds = iy !in 0 until height
                val isCurrentSeat = ix == x && iy == y

                !(widthOufOfBounds || heightOufOfBounds || isCurrentSeat) && grid[iy][ix] == SEAT_OCCUPIED
            }
        }
    }

    private fun countVisibleAdjacentOccupiedSeats(grid: Array<CharArray>, x: Int, y: Int): Int {
        val limit = 1
        return possibleDirections.sumOf { vector ->
            var ix = x
            var iy = y

            while (true) {
                iy += vector.dy
                ix += vector.dx

                val widthOufOfBounds = ix !in 0 until width
                val heightOufOfBounds = iy !in 0 until height

                if (widthOufOfBounds || heightOufOfBounds) {
                    return@sumOf 0
                }

                when (grid[iy][ix]) {
                    SEAT_OCCUPIED -> return@sumOf 1
                    SEAT_EMPTY -> return@sumOf 0
                    else -> continue
                }
            }
        }
    }

    private fun print(grid: Array<CharArray>) {
        grid.forEach { line ->
            line.forEach { char -> print("$char ") }
            println()
        }

        println("---------\n")
    }

    private fun Array<CharArray>.countOccupiedSeats(): Long {
        return sumOf { line ->
            line.count { char -> char == SEAT_OCCUPIED }
        }.toLong()
    }

    private tailrec fun Array<CharArray>.findFinalState(adjacentSeatsLookup: (Array<CharArray>, Int, Int) -> Int): Array<CharArray> {
        print(this)
        val nextState = this.nextState(adjacentSeatsLookup)
        return when {
            this.contentDeepEquals(nextState) -> nextState
            else -> nextState.findFinalState(adjacentSeatsLookup)
        }
    }

    override fun step1() = initialState
        .findFinalState(::countImmediatelyAdjacentOccupiedSeats)
        .countOccupiedSeats()

    override fun step2() = initialState
        .findFinalState(::countVisibleAdjacentOccupiedSeats)
        .countOccupiedSeats()
}