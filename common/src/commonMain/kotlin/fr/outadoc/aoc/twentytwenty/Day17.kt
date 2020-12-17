package fr.outadoc.aoc.twentytwenty

import fr.outadoc.aoc.scaffold.Day
import fr.outadoc.aoc.scaffold.Year

class Day17 : Day(Year.TwentyTwenty) {

    companion object {
        private const val C_INACTIVE = '.'
        private const val C_ACTIVE = '#'
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
        val xRange = (x - reach)..(x + reach)
        val yRange = (y - reach)..(y + reach)
        val zRange = (z - reach)..(z + reach)

        // Remove current point from consideration
        return pointsInRange(xRange, yRange, zRange) - this
    }

    private fun Dimension.next(): Dimension {
        val nextActiveCubes =
            pointsInRange(xRange, yRange, zRange)
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

        return Dimension(activeCubes = nextActiveCubes)
    }

    private fun Dimension.print() {
        zRange.forEach { z ->
            println("z = $z")
            println("┌─${"──".repeat(yRange.count())}┐")

            yRange.forEach { y ->
                print("│ ")

                xRange.map { x ->
                    if (isCubeActive(Point3D(x, y, z))) C_ACTIVE else C_INACTIVE
                }.forEach { c ->
                    print("$c ")
                }

                println("│")
            }

            println("└─${"──".repeat(yRange.count())}┘")
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
        initialState.also {
            it.print()
        }.next().also {
            it.print()
        }.next().also {
            it.print()
        }
        return 0
    }

    override fun step2(): Long {
        TODO("Not yet implemented")
    }
}