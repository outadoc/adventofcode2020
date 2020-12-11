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

    private data class Vector(val dx: Int, val dy: Int)
    private data class Point(val x: Int, val y: Int)

    private operator fun Point.plus(vector: Vector): Point =
        Point(x = x + vector.dx, y = y + vector.dy)

    // We check seats in every direction: horizontally, vertically,
    // and diagonally, both ways.
    private val possibleDirections: List<Vector> =
        (-1..+1).map { dx ->
            (-1..+1).map { dy ->
                Vector(dx, dy)
            }
        }.flatten() - Vector(0, 0)

    private fun Array<CharArray>.nextState(minOccupiedSeatsToBecomeEmpty: Int, maxDistance: Int): Array<CharArray> {
        return this.mapIndexed { iy, line ->
            line.mapIndexed { ix, item ->
                // Count the number of occupied seats around the current seat
                val occupiedSeats = possibleDirections.sumOf { vector ->
                    countOccupiedSeatsVisibleFromSeat(
                        position = Point(ix, iy),
                        vector = vector,
                        maxDistance = maxDistance
                    )
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
        position: Point,
        vector: Vector,
        maxDistance: Int,
        acc: Int = 0,
        distance: Int = 1,
    ): Int {
        val widthOufOfBounds = position.x !in 0 until width
        val heightOufOfBounds = position.y !in 0 until height

        // If we're out of bounds, return the number of seats we've found.
        if (widthOufOfBounds || heightOufOfBounds) {
            return acc
        }

        // Check the seat at the current position
        return when (this[position.y][position.x]) {
            // If it's an occupied seat, we can't see through it, and we just report it
            SEAT_OCCUPIED -> acc + 1

            // If it's an empty seat, we can't see through it,
            // so we report there's no occupied seat on this vector
            SEAT_EMPTY -> acc

            // There's floor here, so we can see further.
            // Advance one spot on our vector and check there
            else -> when {
                distance >= maxDistance -> acc
                else -> countOccupiedSeatsVisibleFromSeat(
                    position = position + vector,
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