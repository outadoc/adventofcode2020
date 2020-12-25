package fr.outadoc.aoc.twentytwenty

import fr.outadoc.aoc.scaffold.Day
import fr.outadoc.aoc.scaffold.Year

class Day24 : Day(Year.TwentyTwenty) {

    private enum class Color { WHITE, BLACK }

    private enum class Direction {
        EAST, SOUTHEAST, SOUTHWEST, WEST, NORTHWEST, NORTHEAST
    }

    private data class Vector(val x: Int, val y: Int)

    private data class Tile(val color: Color)

    private tailrec fun readDirections(rest: String, acc: List<Direction> = emptyList()): List<Direction> {
        if (rest.isEmpty()) return acc
        val dir = when (rest.take(2)) {
            "se" -> Direction.SOUTHEAST
            "sw" -> Direction.SOUTHWEST
            "ne" -> Direction.NORTHEAST
            "nw" -> Direction.NORTHWEST
            else -> when (rest.take(1)) {
                "e" -> Direction.EAST
                "w" -> Direction.WEST
                else -> throw IllegalArgumentException(rest)
            }
        }

        return readDirections(
            rest = rest.drop(if (dir in listOf(Direction.EAST, Direction.WEST)) 1 else 2),
            acc = acc + dir
        )
    }

    private val tilesToFlipOver: List<List<Direction>> =
        readDayInput()
            .lines()
            .map(::readDirections)

    fun step1(): Long {
        tilesToFlipOver.forEach { println(it) }
        TODO()
    }

    fun step2(): Long {
        TODO()
    }
}