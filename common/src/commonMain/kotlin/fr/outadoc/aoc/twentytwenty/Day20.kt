package fr.outadoc.aoc.twentytwenty

import fr.outadoc.aoc.scaffold.Day
import fr.outadoc.aoc.scaffold.Year

class Day20 : Day(Year.TwentyTwenty) {

    private data class Position(val x: Int, val y: Int)

    private data class Tile(val id: Long, val content: List<CharArray>)

    private data class TransformationVector(val x: Int, val y: Int)

    private data class Puzzle(val placedTiles: Map<Position, Tile>)

    private val tiles: List<Tile> =
        readDayInput()
            .split("\n\n")
            .map { tileSection ->
                val tileDescription = tileSection.lines()
                val id = tileDescription
                    .first()
                    .replace("Tile ", "")
                    .replace(":", "")
                    .toLong()

                val tileContent = tileDescription
                    .drop(1)
                    .map { contentLine -> contentLine.toCharArray() }

                Tile(id = id, content = tileContent)
            }

    private val validTransforms: Sequence<TransformationVector> =
        (0..1).asSequence().flatMap { x ->
            (0..1).asSequence().map { y ->
                TransformationVector(x, y)
            }
        }

    private val Tile.possibleVariants: Sequence<Tile>
        get() = validTransforms.map { transform ->
            withTransform(transform)
        }

    private fun Tile.withTransform(transform: TransformationVector): Tile {
        return when (transform) {
            TransformationVector(0, 0) -> this
            TransformationVector(1, 0) -> flipHorizontal()
            TransformationVector(0, 1) -> flipVertical()
            TransformationVector(1, 1) -> flipHorizontal().flipVertical()
            else -> throw IllegalArgumentException("invalid vector: $transform")
        }
    }

    private fun Tile.flipVertical(): Tile {
        return copy(content = content.reversed())
    }

    private fun Tile.flipHorizontal(): Tile {
        return copy(content = content.map { line ->
            line.reversed().toCharArray()
        })
    }

    override fun step1(): Long {
        TODO("Not yet implemented")
    }

    override fun step2(): Long {
        TODO("Not yet implemented")
    }
}