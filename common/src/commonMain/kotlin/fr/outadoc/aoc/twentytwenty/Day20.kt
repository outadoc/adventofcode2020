package fr.outadoc.aoc.twentytwenty

import fr.outadoc.aoc.scaffold.Day
import fr.outadoc.aoc.scaffold.Year

class Day20 : Day(Year.TwentyTwenty) {

    private data class Position(val x: Int, val y: Int)

    private data class Tile(val id: Long, val content: List<String>) {

        private fun flipVertical(): Tile {
            return copy(content = content.reversed())
        }

        private fun flipHorizontal(): Tile {
            return copy(content = content.map { line ->
                line.reversed()
            })
        }

        private fun transpose(): Tile {
            return copy(content = content.mapIndexed { y, line ->
                line.mapIndexed { x, _ -> content[x][y] }.joinToString(separator = "")
            })
        }

        val possibleVariations: List<Tile>
            get() = listOf(
                this,
                flipHorizontal(),
                flipVertical(),
                flipHorizontal().flipVertical(),
                this.transpose(),
                flipHorizontal().transpose(),
                flipVertical().transpose(),
                flipHorizontal().flipVertical().transpose()
            )

        fun sharesRightBorderWith(other: Tile): Boolean {
            // Check if last column of this == first column of other
            return content
                .map { line -> line.last() }.toCharArray()
                .contentEquals(
                    other.content.map { line -> line.first() }.toCharArray()
                )
        }

        fun sharesBottomBorderWith(other: Tile): Boolean {
            // Check if last row of this == first row of other
            return content.last() == other.content.first()
        }
    }

    private data class Puzzle(val iteration: Int = 0, val placedTiles: Map<Position, Tile>) {
        val xRange: IntRange
            get() = placedTiles.keys.minOf { it.x }..placedTiles.keys.maxOf { it.x }

        val yRange: IntRange
            get() = placedTiles.keys.minOf { it.y }..placedTiles.keys.maxOf { it.y }

        val corners: List<Tile>
            get() = listOf(
                Position(xRange.first, yRange.first),
                Position(xRange.last, yRange.first),
                Position(xRange.first, yRange.last),
                Position(xRange.last, yRange.last)
            ).map { pos ->
                placedTiles.getValue(pos)
            }
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

                val tileContent = tileDescription.drop(1)
                Tile(id = id, content = tileContent)
            }

    private val tileHeight = tiles.first().content.size
    private val tileWidth = tiles.first().content.first().length

    private val Position.surrounding: List<Position>
        get() = listOf(
            copy(x = x + 1), // right
            copy(x = x - 1), // left
            copy(y = y + 1), // up
            copy(y = y - 1)  // down
        )

    private val Puzzle.remainingTiles: List<Tile>
        get() = tiles.filterNot { tile -> tile.id in placedTiles.values.map { it.id } }

    private fun Puzzle.nextState(): Puzzle {
        val candidateTiles: List<Tile> =
            remainingTiles.flatMap { remainingTile ->
                remainingTile.possibleVariations
            }

        val candidatePositions = placedTiles.flatMap { (position, _) ->
            position.surrounding
                .filterNot { posAdjacentToPlacedTile ->
                    // Remove positions where there already is a tile
                    posAdjacentToPlacedTile in placedTiles.keys
                }
        }

        // Find all tiles next to which there's an open spot
        return candidatePositions.flatMap { candidatePosition ->
            // Try to find a tile that can fit at this position
            candidateTiles.filter { candidateTile ->
                // Find all placed tiles around this position
                val surroundingTiles = candidatePosition
                    .surrounding
                    .mapNotNull { pos ->
                        placedTiles[pos]?.let { tile -> pos to tile }
                    }

                // Check that all placed tiles around this position can fit the candidate
                surroundingTiles.all { existingTile ->
                    existingTile.fits(candidatePosition to candidateTile)
                }

            }.map { variation ->
                // Falalalala, lala ka-ching!
                copy(
                    iteration = iteration + 1,
                    placedTiles = placedTiles + (candidatePosition to variation)
                )
            }
        }.first()
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

    private fun Puzzle.complete(): Puzzle? {
        return when {
            remainingTiles.isEmpty() -> this
            else -> nextState().complete()
        }
    }

    private fun Tile.print() {
        println("tile #$id")
        content.forEach { line ->
            println(line)
        }
        println()
    }

    private fun Puzzle.print() {
        println("iteration #$iteration")
        yRange.forEach { tileY ->
            (0 until tileHeight).forEach { contentY ->
                xRange.forEach { tileX ->
                    val tile = placedTiles[Position(tileX, tileY)]
                    if (tile != null) {
                        // Print tile row
                        print(tile.content[contentY] + " ")
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

        return p.corners.fold(1) { acc, tile ->
            acc * tile.id
        }
    }

    override fun step2(): Long {
        TODO("Not yet implemented")
    }
}