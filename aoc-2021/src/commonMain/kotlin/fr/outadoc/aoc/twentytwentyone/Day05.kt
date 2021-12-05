package fr.outadoc.aoc.twentytwentyone

import fr.outadoc.aoc.scaffold.Day
import fr.outadoc.aoc.scaffold.readDayInput
import kotlin.math.abs

class Day05 : Day<Int> {

    private data class Point(val x: Int, val y: Int) {
        override fun toString() = "($x,$y)"
    }

    private data class Segment(val a: Point, val b: Point) {
        override fun toString() = "[$a $b]"
    }

    private val input = readDayInput()
        .lineSequence()
        .map { line ->
            val (a, b) = line.split(" -> ").map { point ->
                val (x, y) = point.split(',')
                Point(x.toInt(), y.toInt())
            }

            Segment(a, b)
        }

    private data class State(
        val map: Array<IntArray> = Array(0) { IntArray(0) }
    ) {
        val height: Int = map.size
        val width: Int = map.firstOrNull()?.size ?: 0

        fun copyWithSize(height: Int, width: Int) = copy(
            map = Array(height) { index ->
                map.getOrNull(index)?.copyOf(width) ?: IntArray(width)
            }
        )

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (other == null || this::class != other::class) return false

            other as State

            if (!map.contentDeepEquals(other.map)) return false

            return true
        }

        override fun hashCode(): Int {
            return map.contentDeepHashCode()
        }
    }

    private fun State.reduce(segment: Segment, withDiagonals: Boolean) = with(segment) {
        // Increase map size if necessary
        val height = maxOf(height, a.y + 1, b.y + 1)
        val width = maxOf(width, a.x + 1, b.x + 1)

        copyWithSize(height, width).apply {
            a.rangeTo(b, withDiagonals = withDiagonals).forEach { p ->
                map[p.y][p.x] = map[p.y][p.x] + 1
            }
        }
    }

    private fun Point.rangeTo(other: Point, withDiagonals: Boolean): List<Point> {
        if (x > other.x) return other.rangeTo(this, withDiagonals)
        val coeff = (other.y - y).toFloat() / (other.x - x).toFloat()
        return when {
            // Vertical
            x == other.x -> (minOf(y, other.y)..maxOf(y, other.y)).map { y -> Point(x, y) }
            // Horizontal
            y == other.y -> (minOf(x, other.x)..maxOf(x, other.x)).map { x -> Point(x, y) }
            !withDiagonals -> emptyList()
            coeff == 1f -> (0..abs(other.x - x)).map { i -> Point(x + i, y + i) }
            coeff == -1f -> (0..abs(other.x - x)).map { i -> Point(x + i, y - i) }
            else -> error("unknown range")
        }
    }

    private fun State.countOverlaps(): Int =
        map.sumOf { line ->
            line.sumOf { count ->
                if (count > 1) 1L else 0L
            }
        }.toInt()

    private fun State.print() {
        map.forEach { line ->
            println(
                line.joinToString("  ") {
                    if (it == 0) "." else it.toString()
                }
            )
        }

        println()
        println()
    }

    override fun step1(): Int = input
        .fold(State()) { acc, segment ->
            acc.reduce(segment, withDiagonals = false)
        }
        .countOverlaps()

    override fun step2(): Int = input
        .fold(State()) { acc, segment ->
            acc.reduce(segment, withDiagonals = true)
        }
        .countOverlaps()

    override val expectedStep1 = 8060
    override val expectedStep2 = 21577
}
