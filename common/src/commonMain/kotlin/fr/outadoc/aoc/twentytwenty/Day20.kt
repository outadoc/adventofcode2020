package fr.outadoc.aoc.twentytwenty

import fr.outadoc.aoc.scaffold.Day
import fr.outadoc.aoc.scaffold.Year

class Day20 : Day(Year.TwentyTwenty) {

    private data class Position(val x: Int, val y: Int)

    private data class Tile(val id: Long, val content: List<CharArray>) {

        private val validTransforms: Sequence<TransformationVector> =
            (0..1).asSequence().flatMap { x ->
                (0..1).asSequence().map { y ->
                    TransformationVector(x, y)
                }
            }

        private fun flipVertical(): Tile {
            return copy(content = content.reversed())
        }

        private fun flipHorizontal(): Tile {
            return copy(content = content.map { line ->
                line.reversed().toCharArray()
            })
        }

        private fun withTransform(transform: TransformationVector): Tile {
            return when (transform) {
                TransformationVector(0, 0) -> this
                TransformationVector(1, 0) -> flipHorizontal()
                TransformationVector(0, 1) -> flipVertical()
                TransformationVector(1, 1) -> flipHorizontal().flipVertical()
                else -> throw IllegalArgumentException("invalid vector: $transform")
            }
        }

        val possibleVariations: Sequence<Tile> =
            validTransforms.map { transform ->
                withTransform(transform)
            }
    }

    private data class TransformationVector(val x: Int, val y: Int)

    private data class Puzzle(val iteration: Int = 0, val placedTiles: Map<Position, Tile>) {
        val xRange: IntRange
            get() = placedTiles.keys.minOf { it.x }..placedTiles.keys.maxOf { it.x }

        val yRange: IntRange
            get() = placedTiles.keys.minOf { it.y }..placedTiles.keys.maxOf { it.y }
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


    private val Position.surrounding: List<Position>
        get() = listOf(
            copy(x = x + 1), // right
            copy(x = x - 1), // left
            copy(y = y + 1), // up
            copy(y = y - 1)  // down
        )

    private val Puzzle.remainingTiles: List<Tile>
        get() = tiles.filterNot { tile -> tile.id in placedTiles.values.map { it.id } }

    private fun Puzzle.possibleNextStates(): List<Puzzle> {
        return placedTiles.flatMap { (pos, placedTile) ->
            val currentTile = pos to placedTile

            pos.surrounding.filterNot { surroundingPos ->
                // Remove positions where there already is a tile
                surroundingPos in placedTiles.keys
            }.flatMap { surroundingPos ->
                // Try to find a tile that can fit at this position
                remainingTiles.flatMap { remainingTile ->
                    remainingTile.possibleVariations
                }.filter { variation ->
                    currentTile.fits(surroundingPos to variation)
                }.map { variation ->
                    // Falalalala, lala ka-ching!
                    copy(
                        iteration = iteration + 1,
                        placedTiles = placedTiles + (surroundingPos to variation)
                    )
                }
            }
        }
    }

    private fun Pair<Position, Tile>.fits(other: Pair<Position, Tile>): Boolean {
        val (pos, tile) = this
        val (otherPos, otherTile) = other

        val deltaX = otherPos.x - pos.x
        val deltaY = otherPos.y - pos.y

        return when {
            // Other tile is right of the current one
            deltaX == 1 -> tile.sharesRightBorderWith(otherTile)
            // Other tile is left of the current one
            deltaX == -1 -> otherTile.sharesRightBorderWith(tile)
            // Other tile is on bottom of the current one
            deltaY == 1 -> tile.sharesBottomBorderWith(otherTile)
            // Other tile is on top of the current one
            deltaY == -1 -> otherTile.sharesBottomBorderWith(tile)
            else -> throw IllegalStateException("deltaX = $deltaX, deltaY = $deltaY -> invalid")
        }
    }

    private fun Tile.sharesRightBorderWith(other: Tile): Boolean {
        // Check if last column of this == first column of other
        return content
            .map { line -> line.last() }.toCharArray()
            .contentEquals(
                other.content.map { line -> line.first() }.toCharArray()
            )
    }

    private fun Tile.sharesBottomBorderWith(other: Tile): Boolean {
        // Check if last row of this == first row of other
        return content.last().contentEquals(other.content.first())
    }

    private fun Puzzle.complete(): Puzzle? {
        //print()
        return when {
            remainingTiles.isEmpty() -> this
            else -> possibleNextStates()
                .mapNotNull { next -> next.complete() }
                .firstOrNull()
        }
    }

    private fun Puzzle.print() {
        println("iteration #$iteration")
        yRange.forEach { tileY ->
            (0 until tileHeight).forEach { contentY ->
                xRange.forEach { tileX ->
                    val tile = placedTiles[Position(tileX, tileY)]
                    if (tile != null) {
                        // Print tile row
                        print(tile.content[contentY].joinToString(separator = "") + " ")
                    } else {
                        // Print empty row
                        print(" ".repeat(tileWidth + 1))
                    }
                }
                println()
            }
            println()
        }
    }

    private val initialState: Puzzle =
        Puzzle(
            placedTiles = mapOf(
                Position(0, 0) to tiles.first()
            )
        )

    override fun step1(): Long {
        val p = initialState.complete()!!

        println("found final state")
        p.print()

        return -1
    }

    override fun step2(): Long {
        TODO("Not yet implemented")
    }
}