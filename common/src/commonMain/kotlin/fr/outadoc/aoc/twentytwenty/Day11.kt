package fr.outadoc.aoc.twentytwenty

import fr.outadoc.aoc.scaffold.Day
import fr.outadoc.aoc.scaffold.Year

class Day11 : Day(Year.TwentyTwenty) {

    companion object {
        private const val SEAT_EMPTY = 'L'
        private const val SEAT_OCCUPIED = '#'

        private const val PRINT_DEBUG = false
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

    private fun Array<CharArray>.nextState(minOccupiedSeatsToBecomeEmpty: Int, maxDepth: Int): Array<CharArray> {
        return this.mapIndexed { iy, line ->
            line.mapIndexed { ix, item ->
                val occupied = countAdjacentOccupiedSeats(this, ix, iy, maxDepth)
                when {
                    item == SEAT_EMPTY && occupied == 0 -> SEAT_OCCUPIED
                    item == SEAT_OCCUPIED && occupied >= minOccupiedSeatsToBecomeEmpty -> SEAT_EMPTY
                    else -> item
                }
            }.toCharArray()
        }.toTypedArray()
    }

    private fun countAdjacentOccupiedSeats(grid: Array<CharArray>, x: Int, y: Int, maxDepth: Int): Int {
        return possibleDirections.sumOf { vector ->
            countVisibleOccupiedSeats(grid, x + vector.dx, y + vector.dy, vector, maxDepth = maxDepth)
        }
    }

    private tailrec fun countVisibleOccupiedSeats(
        grid: Array<CharArray>,
        x: Int,
        y: Int,
        vector: Vector,
        maxDepth: Int,
        acc: Int = 0,
        depth: Int = 1,
    ): Int {
        val widthOufOfBounds = x !in 0 until width
        val heightOufOfBounds = y !in 0 until height

        // If we've reached the limit, or we're out of bounds, return what we have
        if (widthOufOfBounds || heightOufOfBounds) {
            return acc
        }

        return when (grid[y][x]) {
            SEAT_OCCUPIED -> acc + 1
            SEAT_EMPTY -> acc
            else -> when {
                depth >= maxDepth -> acc
                else -> countVisibleOccupiedSeats(
                    grid = grid,
                    x = x + vector.dx,
                    y = y + vector.dy,
                    vector = vector,
                    acc = acc,
                    depth = depth + 1,
                    maxDepth = maxDepth
                )
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

    private tailrec fun Array<CharArray>.findFinalState(
        minOccupiedSeatsToBecomeEmpty: Int,
        maxDepth: Int
    ): Array<CharArray> {
        if (PRINT_DEBUG) print(this)

        val nextState = this.nextState(minOccupiedSeatsToBecomeEmpty, maxDepth)
        return when {
            this.contentDeepEquals(nextState) -> nextState
            else -> nextState.findFinalState(minOccupiedSeatsToBecomeEmpty, maxDepth)
        }
    }

    override fun step1() = initialState
        .findFinalState(minOccupiedSeatsToBecomeEmpty = 4, maxDepth = 1)
        .countOccupiedSeats()

    override fun step2() = initialState
        .findFinalState(minOccupiedSeatsToBecomeEmpty = 5, maxDepth = Int.MAX_VALUE)
        .countOccupiedSeats()
}