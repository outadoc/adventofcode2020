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

    private data class State(val tiles: Map<Vector, Tile> = emptyMap()) {
        val xRange: IntRange
            get() = (tiles.keys.minOf { pos -> pos.x } - 1)..(tiles.keys.maxOf { pos -> pos.x } + 1)
        val yRange: IntRange
            get() = (tiles.keys.minOf { pos -> pos.y } - 1)..(tiles.keys.maxOf { pos -> pos.y } + 1)
    }

    private fun State.addPath(path: List<Direction>): State {
        val tilePosition: Vector = path.fold(Vector(0, 0)) { acc, direction ->
            acc + direction.asVector
        }

        val flippedTile = tiles.getOrElse(tilePosition) { Tile() }.let { tile ->
            tile.copy(color = tile.color.flip())
        }

        return copy(tiles = tiles + (tilePosition to flippedTile))
    }

    private val Vector.adjacent: List<Vector>
        get() = Direction.values().map { direction ->
            this + direction.asVector
        }

    private fun State.countAdjacentBlackTiles(vector: Vector): Int {
        return vector.adjacent.count { adjacent ->
            tiles.getOrElse(adjacent) { Tile() }.color == Color.BLACK
        }
    }

    private fun State.nextDay(): State {
        return copy(
            tiles = yRange.flatMap { y ->
                xRange.map { x ->
                    val pos = Vector(x, y)
                    val tile = tiles.getOrElse(pos) { Tile() }
                    pos to
                        tile.copy(
                            color = when (tile.color) {
                                Color.BLACK -> when (countAdjacentBlackTiles(pos)) {
                                    1, 2 -> tile.color
                                    else -> tile.color.flip()
                                }
                                Color.WHITE -> when (countAdjacentBlackTiles(pos)) {
                                    2 -> tile.color.flip()
                                    else -> tile.color
                                }
                            }
                        )
                }
            }.toMap()
        )
    }

    private val tilesToFlipOver: List<List<Direction>> =
        readDayInput()
            .lines()
            .map(::readDirections)

    private fun State.nthIteration(n: Int): State {
        return (0 until n).foldIndexed(this) { i, state, _ ->
            println("day ${i + 1}: ${state.countBlackTiles()}")
            state.nextDay()
        }
    }

    private fun State.countBlackTiles(): Int =
        tiles.count { (_, tile) ->
            tile.color == Color.BLACK
        }

    fun step1(): Int {
        return tilesToFlipOver
            .fold(State()) { acc, path ->
                acc.addPath(path)
            }
            .countBlackTiles()
    }

    fun step2(): Int {
        return tilesToFlipOver
            .fold(State()) { acc, path ->
                acc.addPath(path)
            }
            .nthIteration(100)
            .countBlackTiles()
    }
}