package fr.outadoc.aoc.twentytwenty

import fr.outadoc.aoc.scaffold.Day
import fr.outadoc.aoc.scaffold.Year

class Day20 : Day(Year.TwentyTwenty) {

    private data class Position(val x: Int, val y: Int)

    private data class Tile(val id: Long, val content: List<CharArray>)

    private data class TransformationVector(val x: Int, val y: Int)

    private data class Puzzle(val placedTiles: Map<Position, Tile>) {
        val xRange: IntRange by lazy { placedTiles.keys.minOf { it.x }..placedTiles.keys.maxOf { it.x } }
        val yRange: IntRange by lazy { placedTiles.keys.minOf { it.y }..placedTiles.keys.maxOf { it.y } }
    }

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

    private val tileHeight = tiles.first().content.size
    private val tileWidth = tiles.first().content.first().size

    private val validTransforms: Sequence<TransformationVector> =
        (0..1).asSequence().flatMap { x ->
            (0..1).asSequence().map { y ->
                TransformationVector(x, y)
            }
        }

    private val Tile.possibleVariations: Sequence<Tile>
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

    private fun Puzzle.placeNextTile(): Puzzle {
        val remainingTiles: List<Tile> = tiles - placedTiles.values
        
    }

    private fun Puzzle.print(): Puzzle {
        yRange.forEach { tileY ->
            (0 until tileHeight).forEach { contentY ->
                xRange.forEach { tileX ->
                    val tile = placedTiles[Position(tileX, tileY)]
                    if (tile != null) {
                        // Print tile row
                        print(tile.content[contentY].toString() + " ")
                    } else {
                        // Print empty row
                        print(" ".repeat(tileWidth + 1))
                    }
                }
            }
        }
    }

    override fun step1(): Long {
        TODO("Not yet implemented")
    }

    override fun step2(): Long {
        TODO("Not yet implemented")
    }
}