package fr.outadoc.aoc.twentytwenty

import fr.outadoc.aoc.scaffold.Day
import fr.outadoc.aoc.scaffold.Year

class Day17 : Day(Year.TwentyTwenty) {

    companion object {
        private const val PRINT_DEBUG = false
    }

    private data class Point4D(val x: Int, val y: Int, val z: Int, val w: Int)

    private data class Dimension(val iteration: Int = 0, val activeCubes: List<Point4D>) {

        // Add some extra room around existing cubes so that we can add new ones
        private val rangePadding = 1

        private fun getRangeForAxis(axis: (Point4D) -> Int): IntRange =
            (activeCubes.minOf(axis) - rangePadding)..(activeCubes.maxOf(axis) + rangePadding)

        val xRange: IntRange by lazy { getRangeForAxis { it.x } }
        val yRange: IntRange by lazy { getRangeForAxis { it.y } }
        val zRange: IntRange by lazy { getRangeForAxis { it.z } }
        val wRange: IntRange by lazy { getRangeForAxis { it.w } }

        private val activeCubeLookup = activeCubes.map { it.hashCode() to it }.toMap()

        val Point4D.isActive: Boolean
            get() = activeCubeLookup.containsKey(hashCode())
    }

    private fun pointsInRange(xRange: IntRange, yRange: IntRange, zRange: IntRange, wRange: IntRange): List<Point4D> {
        return wRange.flatMap { w: Int ->
            zRange.flatMap { z: Int ->
                yRange.flatMap { y: Int ->
                    xRange.map { x: Int ->
                        Point4D(x, y, z, w)
                    }
                }
            }
        }
    }

    private fun Point4D.getNeighbors(dimensionCount: Int): List<Point4D> {
        val reach = 1
        return pointsInRange(
            xRange = (x - reach)..(x + reach),
            yRange = (y - reach)..(y + reach),
            zRange = (z - reach)..(z + reach),
            wRange = if (dimensionCount > 3) (w - reach)..(w + reach) else 0..0
        ) - this // Remove current point from consideration
    }

    private fun Dimension.next(dimensionCount: Int): Dimension = Dimension(
        iteration = iteration + 1,
        activeCubes = pointsInRange(
            xRange,
            yRange,
            zRange,
            wRange = if (dimensionCount > 3) wRange else 0..0
        ).mapNotNull { cube ->
            val isActive = cube.isActive
            val activeNeighborCount = cube
                .getNeighbors(dimensionCount)
                .count { neighbor ->
                    neighbor.isActive
                }

            when {
                isActive && activeNeighborCount in 2..3 -> {
                    // If a cube is active and exactly 2 or 3 of its neighbors are also active, the cube remains active.
                    cube
                }
                isActive -> {
                    // Otherwise, the cube becomes inactive.
                    null
                }
                activeNeighborCount == 3 -> {
                    // If a cube is inactive but exactly 3 of its neighbors are active, the cube becomes active.
                    cube
                }
                else -> {
                    // Otherwise, the cube remains inactive.
                    null
                }
            }
        }
    )

    private fun Dimension.nthIteration(dimensionCount: Int, n: Int): Dimension {
        return (0 until n).fold(this) { dimension, _ ->
            dimension.next(dimensionCount).also {
                if (PRINT_DEBUG) it.print()
            }
        }
    }

    private fun Dimension.print() {
        println("=== iteration #$iteration ===")

        wRange.forEach { w ->
            zRange.forEach { z ->
                println("z = $z, w = $w")
                println("┌─${"──".repeat(xRange.count())}┐")

                yRange.forEach { y ->
                    print("│ ")

                    xRange.map { x ->
                        if (Point4D(x, y, z, w).isActive) '■' else '.'
                    }.forEach { c ->
                        print("$c ")
                    }

                    println("│")
                }

                println("└─${"──".repeat(xRange.count())}┘")
            }
        }
    }

    private val initialLayer: List<Point4D> =
        readDayInput()
            .lines()
            .flatMapIndexed { y, line ->
                line.mapIndexedNotNull { x, c ->
                    when (c) {
                        '#' -> Point4D(x = x, y = y, z = 0, w = 0)
                        else -> null
                    }
                }
            }

    private val initialState: Dimension =
        Dimension(activeCubes = initialLayer)

    fun step1(): Int {
        return initialState
            .nthIteration(dimensionCount = 3, n = 6)
            .activeCubes.size
    }

    fun step2(): Int {
        return initialState
            .nthIteration(dimensionCount = 4, n = 6)
            .activeCubes.size
    }
}