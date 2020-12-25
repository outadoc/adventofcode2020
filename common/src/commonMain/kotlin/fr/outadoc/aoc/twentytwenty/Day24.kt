package fr.outadoc.aoc.twentytwenty

import fr.outadoc.aoc.scaffold.Day
import fr.outadoc.aoc.scaffold.Year

class Day24 : Day(Year.TwentyTwenty) {

    private enum class Color { WHITE, BLACK }

    private fun Color.flip(): Color = when (this) {
        Color.WHITE -> Color.BLACK
        Color.BLACK -> Color.WHITE
    }

    private enum class Direction {
        EAST, SOUTHEAST, SOUTHWEST, WEST, NORTHWEST, NORTHEAST
    }

    private data class Vector(val x: Int, val y: Int)

    private operator fun Vector.plus(vector: Vector) = Vector(
        x = x + vector.x,
        y = y + vector.y
    )

    private data class Tile(val color: Color = Color.WHITE)

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

    private val Direction.asVector: Vector
        get() = when (this) {
            Direction.EAST -> Vector(x = 1, y = 0)
            Direction.WEST -> Vector(x = -1, y = 0)
            Direction.SOUTHEAST -> Vector(x = 1, y = -1)
            Direction.SOUTHWEST -> Vector(x = 0, y = -1)
            Direction.NORTHWEST -> Vector(x = -1, y = 1)
            Direction.NORTHEAST -> Vector(x = 0, y = 1)
        }

    private data class State(val tiles: Map<Vector, Tile> = emptyMap())

    private fun State.reduce(path: List<Direction>): State {
        val tilePosition: Vector = path.fold(Vector(0, 0)) { acc, direction ->
            acc + direction.asVector
        }

        val flippedTile = tiles.getOrElse(tilePosition) { Tile() }.let { tile ->
            tile.copy(color = tile.color.flip())
        }

        return copy(tiles = tiles + (tilePosition to flippedTile))
    }

    private val tilesToFlipOver: List<List<Direction>> =
        readDayInput()
            .lines()
            .map(::readDirections)

    fun step1(): Int {
        return tilesToFlipOver
            .fold(State()) { acc, path ->
                acc.reduce(path)
            }
            .tiles.count { (_, tile) ->
                tile.color == Color.BLACK
            }
    }

    fun step2(): Long {
        TODO()
    }
}