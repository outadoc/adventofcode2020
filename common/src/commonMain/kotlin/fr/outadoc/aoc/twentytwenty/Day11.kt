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
    data class Point(val x: Int, val y: Int)

    private val possibleDirections: List<Vector> =
        (-1..+1).map { dx ->
            (-1..+1).map { dy ->
                Vector(dx, dy)
            }
        }.flatten() - Vector(0, 0)

    private fun Array<CharArray>.nextState(minOccupiedSeatsToBecomeEmpty: Int, maxDistance: Int): Array<CharArray> {
        return this.mapIndexed { iy, line ->
            line.mapIndexed { ix, item ->
                val occupiedSeats = possibleDirections.sumOf { vector ->
                    countOccupiedSeatsVisibleFromSeat(x = ix, y = iy, vector = vector, maxDistance = maxDistance)
                }

                when {
                    item == SEAT_EMPTY && occupiedSeats == 0 -> SEAT_OCCUPIED
                    item == SEAT_OCCUPIED && occupiedSeats >= minOccupiedSeatsToBecomeEmpty -> SEAT_EMPTY
                    else -> item
                }
            }.toCharArray()
        }.toTypedArray()
    }

    private tailrec fun Array<CharArray>.countOccupiedSeatsVisibleFromSeat(
        x: Int,
        y: Int,
        vector: Vector,
        maxDistance: Int,
        acc: Int = 0,
        distance: Int = 1,
    ): Int {
        val widthOufOfBounds = x !in 0 until width
        val heightOufOfBounds = y !in 0 until height

        // If we've reached the limit, or we're out of bounds, return what we have
        if (widthOufOfBounds || heightOufOfBounds) {
            return acc
        }

        return when (this[y][x]) {
            SEAT_OCCUPIED -> acc + 1
            SEAT_EMPTY -> acc
            else -> when {
                distance >= maxDistance -> acc
                else -> countOccupiedSeatsVisibleFromSeat(
                    x = x + vector.dx,
                    y = y + vector.dy,
                    vector = vector,
                    acc = acc,
                    distance = distance + 1,
                    maxDistance = maxDistance
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
        maxDistance: Int
    ): Array<CharArray> {
        if (PRINT_DEBUG) print(this)

        val nextState = this.nextState(minOccupiedSeatsToBecomeEmpty, maxDistance)
        return when {
            this.contentDeepEquals(nextState) -> nextState
            else -> nextState.findFinalState(minOccupiedSeatsToBecomeEmpty, maxDistance)
        }
    }

    override fun step1() = initialState
        .findFinalState(minOccupiedSeatsToBecomeEmpty = 4, maxDistance = 1)
        .countOccupiedSeats()

    override fun step2() = initialState
        .findFinalState(minOccupiedSeatsToBecomeEmpty = 5, maxDistance = Int.MAX_VALUE)
        .countOccupiedSeats()
}