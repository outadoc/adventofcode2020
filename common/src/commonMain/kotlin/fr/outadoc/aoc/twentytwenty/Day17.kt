package fr.outadoc.aoc.twentytwenty

import fr.outadoc.aoc.scaffold.Day
import fr.outadoc.aoc.scaffold.Year

class Day17 : Day(Year.TwentyTwenty) {

    companion object {
        const val C_INACTIVE = '.'
        const val C_ACTIVE = '#'
    }

    private data class Point3D(val x: Int, val y: Int, val z: Int)

    private data class Dimension(val activeCubes: List<Point3D>) {

        val xRange: IntRange by lazy {
            activeCubes.minOf { point -> point.x }..activeCubes.maxOf { point -> point.x }
        }

        val yRange: IntRange by lazy {
            activeCubes.minOf { point -> point.y }..activeCubes.maxOf { point -> point.y }
        }

        val zRange: IntRange by lazy {
            activeCubes.minOf { point -> point.z }..activeCubes.maxOf { point -> point.z }
        }

        fun isCubeActive(coords: Point3D): Boolean {
            return coords in activeCubes
        }
    }

    private fun Dimension.next(): Dimension {
        return this
    }

    private fun print(dimension: Dimension) {

    }

    override fun step1(): Long {
        TODO("Not yet implemented")
    }

    override fun step2(): Long {
        TODO("Not yet implemented")
    }
}