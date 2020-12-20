package fr.outadoc.aoc.twentytwenty

import fr.outadoc.aoc.scaffold.Day
import fr.outadoc.aoc.scaffold.Year

class Day20 : Day(Year.TwentyTwenty) {

    private data class Position(val x: Int, val y: Int)

    private data class Tile(val id: Int, val content: List<CharArray>)

    private data class TransformationVector(val x: Int, val y: Int)
    private data class TransformedTile(val tile: Tile, val transform: TransformationVector)

    override fun step1(): Long {
        TODO("Not yet implemented")
    }

    override fun step2(): Long {
        TODO("Not yet implemented")
    }
}