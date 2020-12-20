package fr.outadoc.aoc.twentytwenty

import fr.outadoc.aoc.scaffold.Day
import fr.outadoc.aoc.scaffold.Year

class Day20 : Day(Year.TwentyTwenty) {

    private data class Position(val x: Int, val y: Int)

    private data class Tile(val id: Long, val content: List<CharArray>)

    private data class TransformationVector(val x: Int, val y: Int)
    private data class TransformedTile(val tile: Tile, val transform: TransformationVector)

    private data class Puzzle(val placedTiles: Map<Position, TransformedTile>)

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

    private val Tile.possibleVariants: Sequence<TransformedTile>
        get() {
            TODO()
        }

    override fun step1(): Long {
        TODO("Not yet implemented")
    }

    override fun step2(): Long {
        TODO("Not yet implemented")
    }
}