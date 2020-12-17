package fr.outadoc.aoc.twentytwenty

import fr.outadoc.aoc.scaffold.Day
import fr.outadoc.aoc.scaffold.Year

class Day17 : Day(Year.TwentyTwenty) {

    companion object {
        const val C_EMPTY = '.'
    }

    data class Point3D(val x: Int, val y: Int, val z: Int)

    data class Dimension(val points: Map<Point3D, Char>) {
        operator fun get(x: Int, y: Int, z: Int): Char {
            return get(Point3D(x, y, z))
        }

        operator fun get(point: Point3D): Char {
            return points[point] ?: C_EMPTY
        }
    }

    fun Dimension.next(): Dimension {
        return this
    }

    fun print(dimension: Dimension) {

    }

    override fun step1(): Long {
        TODO("Not yet implemented")
    }

    override fun step2(): Long {
        TODO("Not yet implemented")
    }
}