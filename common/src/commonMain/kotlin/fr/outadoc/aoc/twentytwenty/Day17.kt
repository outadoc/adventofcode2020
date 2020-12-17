package fr.outadoc.aoc.twentytwenty

import fr.outadoc.aoc.scaffold.Day
import fr.outadoc.aoc.scaffold.Year

class Day17 : Day(Year.TwentyTwenty) {

    companion object {
        private const val C_INACTIVE = '.'
        private const val C_ACTIVE = '#'
    }

    private data class Point3D(val x: Int, val y: Int, val z: Int)

    private data class Dimension(val iteration: Int = 0, val activeCubes: List<Point3D>) {

        // Add some extra room around existing cubes so that we can add new ones
        private val rangePadding = 1

        val xRange: IntRange by lazy {
            (activeCubes.minOf { point -> point.x } - rangePadding)..(activeCubes.maxOf { point -> point.x } + rangePadding)
        }

        val yRange: IntRange by lazy {
            (activeCubes.minOf { point -> point.y } - rangePadding)..(activeCubes.maxOf { point -> point.y } + rangePadding)
        }

        val zRange: IntRange by lazy {
            (activeCubes.minOf { point -> point.z } - rangePadding)..(activeCubes.maxOf { point -> point.z } + rangePadding)
        }

        fun isCubeActive(coords: Point3D): Boolean {
            return coords in activeCubes
        }
    }

    private fun pointsInRange(xRange: IntRange, yRange: IntRange, zRange: IntRange): List<Point3D> {
        return zRange.flatMap { z: Int ->
            yRange.flatMap { y: Int ->
                xRange.map { x: Int ->
                    Point3D(x, y, z)
                }
            }
        }
    }

    private fun Point3D.getNeighbors(reach: Int = 1): List<Point3D> {
        return pointsInRange(
            xRange = (x - reach)..(x + reach),
            yRange = (y - reach)..(y + reach),
            zRange = (z - reach)..(z + reach)
        ) - this // Remove current point from consideration
    }

    private fun Dimension.next(): Dimension = Dimension(
        iteration = iteration + 1,
        activeCubes = pointsInRange(xRange, yRange, zRange)
            .mapNotNull { cube ->
                val isActive = cube in activeCubes
                val activeNeighborCount =
                    cube.getNeighbors()
                        .count { neighbor ->
                            neighbor in activeCubes
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

    private fun Dimension.nthIteration(n: Int): Dimension {
        return (0 until n).fold(this) { dimension, _ ->
            dimension.next().also {
                it.print()
            }
        }
    }

    private fun Dimension.print() {
        println("=== iteration #$iteration ===")

        zRange.forEach { z ->
            println("z = $z")
            println("┌─${"──".repeat(xRange.count())}┐")

            yRange.forEach { y ->
                print("│ ")

                xRange.map { x ->
                    if (isCubeActive(Point3D(x, y, z))) C_ACTIVE else C_INACTIVE
                }.forEach { c ->
                    print("$c ")
                }

                println("│")
            }

            println("└─${"──".repeat(xRange.count())}┘")
        }
    }

    private val initialLayer: List<Point3D> =
        readDayInput()
            .lines()
            .flatMapIndexed { y, line ->
                line.mapIndexedNotNull { x, c ->
                    if (c == C_ACTIVE) {
                        Point3D(x = x, y = y, z = 0)
                    } else null
                }
            }

    private val initialState: Dimension =
        Dimension(activeCubes = initialLayer)

    override fun step1(): Long {
        return initialState.nthIteration(6).activeCubes.size.toLong()
    }

    override fun step2(): Long {
        TODO("Not yet implemented")
    }
}