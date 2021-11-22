package fr.outadoc.aoc.twentytwenty

import fr.outadoc.aoc.scaffold.Day
import fr.outadoc.aoc.scaffold.Year

class Day20 : Day(Year.TwentyTwenty) {

    companion object {
        private const val PRINT_DEBUG = false
    }

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

        val tileHeight: Int
            get() = placedTiles.values.first().content.size

        val tileWidth: Int
            get() = placedTiles.values.first().content.first().length

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

    private fun Puzzle.complete(): Puzzle {
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

    private fun Puzzle.trimmed(): Puzzle {
        return copy(
            placedTiles = placedTiles.map { (pos, tile) ->
                pos to tile.copy(content = tile.content
                    .drop(1)
                    .dropLast(1)
                    .map { line -> line.drop(1).dropLast(1) }
                )
            }.toMap()
        )
    }

    private fun Puzzle.toImage(): Tile {
        val tileContent: List<String> =
            yRange.flatMap { tileY ->
                (0 until tileHeight).map { contentY ->
                    xRange.joinToString(separator = "") { tileX ->
                        val tile = placedTiles.getValue(Position(tileX, tileY))
                        tile.content[contentY]
                    }
                }
            }

        return Tile(id = -1, content = tileContent)
    }

    private val seaMonster = "                  # \n" +
        "#    ##    ##    ###\n" +
        " #  #  #  #  #  #   "

    private val seaMonsterLength = seaMonster.lines().first().length
    private val seaRegexes = seaMonster.lines().map { line ->
        Regex("^" + line.replace(' ', '.') + "$")
    }

    private fun Tile.countSeaMonsters(): Long {
        return content
            .dropLast(seaRegexes.size - 1)
            .mapIndexed { lineIdx, line ->
                // For each line in the picture
                // For each next line of the monster, check if it matches the corresponding regex
                // We've found that there might be a monster at startIdx — or at least its first line.
                // Find occurrences of the monster's first line!
                // Extract substrings that might match the monster in length
                (0..(line.length - seaMonsterLength)).map { startIdx ->
                    // Extract substrings that might match the monster in length
                    startIdx to line.substring(startIdx, startIdx + seaMonsterLength)
                }.filter { (_, possibleMonsterChunk) ->
                    // Find occurrences of the monster's first line!
                    val regex = seaRegexes.first()
                    regex.matches(possibleMonsterChunk)
                }.count { (startIdx, _) ->
                    // We've found that there might be a monster at startIdx — or at least its first line.
                    seaRegexes.drop(1)
                        .mapIndexed { regexIdx, regex -> regexIdx to regex }
                        .all { (regexIdx, regex) ->
                            // For each next line of the monster, check if it matches the corresponding regex
                            val nextLineIdx = lineIdx + regexIdx + 1
                            val checkedString =
                                content[nextLineIdx].substring(startIdx, startIdx + seaMonsterLength)
                            regex.matches(checkedString)
                        }
                }
            }.sum().toLong()
    }

    private fun Tile.getWaterRoughness(): Long {
        val seaMonsterHashes = seaMonster.count { it == '#' }
        val totalHashes = content
            .joinToString(separator = "")
            .count { it == '#' }

        return possibleVariations.sumOf { variation ->
            variation.countSeaMonsters().let { seaMonsterCount ->
                if (seaMonsterCount > 0) {
                    totalHashes - seaMonsterHashes * seaMonsterCount
                } else 0
            }
        }
    }

    fun step1(): Long {
        return initialState
            .complete()
            .also { finalState ->
                if (PRINT_DEBUG) {
                    finalState.print()
                }
            }
            .corners
            .also { corners ->
                if (PRINT_DEBUG) {
                    println(corners)
                }
            }
            .fold(1) { acc, tile ->
                acc * tile.id
            }
    }

    fun step2(): Long {
        return initialState
            .complete()
            .also { finalState ->
                if (PRINT_DEBUG) {
                    finalState.print()
                }
            }
            .trimmed()
            .toImage()
            .also { bigAssTile ->
                if (PRINT_DEBUG) {
                    bigAssTile.print()
                }
            }.getWaterRoughness()
    }
}