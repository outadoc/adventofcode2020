package fr.outadoc.aoc.twentynineteen

import fr.outadoc.aoc.scaffold.Day
import fr.outadoc.aoc.scaffold.Year
import fr.outadoc.aoc.scaffold.max
import fr.outadoc.aoc.scaffold.min
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

class Day03 : Day(Year.TwentyNineteen) {

    private val input: List<String> =
        readDayInput().lines()

    data class Point(val x: Int, val y: Int) {

        operator fun plus(o: Point): Point {
            return Point(x + o.x, y + o.y)
        }

        fun distance(o: Point): Int {
            return abs(x - o.x) + abs(y - o.y)
        }
    }

    data class Segment(val a: Point, val b: Point) {

        private val isVertical = a.x == b.x
        private val isHorizontal = a.y == b.y

        private val rightMostPoint = if (a.x >= b.x) a else b
        private val leftMostPoint = if (a.x >= b.x) b else a
        private val topMostPoint = if (a.y >= b.y) b else a
        private val bottomMostPoint = if (a.y >= b.y) a else b

        private fun isPerpendicularTo(o: Segment): Boolean {
            return when {
                isVertical -> o.isHorizontal
                isHorizontal -> o.isVertical
                else -> throw IllegalStateException("Segment must be aligned on the grid")
            }
        }

        fun intersectsAt(o: Segment): Point? {
            if (!isPerpendicularTo(o)) {
                return null
            }

            val vSeg = if (isVertical) this else o
            val hSeg = if (isHorizontal) this else o

            return when {
                vSeg.topMostPoint.y >= hSeg.a.y ||
                        hSeg.leftMostPoint.x >= vSeg.a.x ||
                        vSeg.bottomMostPoint.y <= hSeg.a.y ||
                        hSeg.rightMostPoint.x <= vSeg.a.x -> {
                    null
                }
                else -> {
                    Point(hSeg.a.x, vSeg.a.y)
                }
            }
        }
    }

    data class Wire(val segments: List<Segment>) {

        fun crossesVerticallyAt(x: Int, y: Int): Boolean {
            return segments.any { s ->
                s.a.x == s.b.x // x is constant
                        && s.a.x == x // x is the column that interests us
                        && (y in (s.a.y..s.b.y) || y in (s.b.y..s.a.y)) // the segment exists at row y
            }
        }

        fun crossesHorizontallyAt(x: Int, y: Int): Boolean {
            return segments.any { s ->
                s.a.y == s.b.y // y is constant
                        && s.a.y == y // y is the row that interests us
                        && (x in (s.a.x..s.b.x) || x in (s.b.x..s.a.x)) // the segment exists at column x
            }
        }

        fun intersectsAt(o: Wire): List<Point> {
            return segments.flatMap { s1 ->
                o.segments.mapNotNull { s2 ->
                    s1.intersectsAt(s2)
                }
            }
        }
    }

    data class Circuit(val wires: List<Wire>) {

        val minX: Int by lazy {
            wires.map { w ->
                w.segments.map { s ->
                    min(s.a.x, s.b.x)
                }.min()
            }.min()
        }

        val maxX: Int by lazy {
            wires.map { w ->
                w.segments.map { s ->
                    max(s.a.x, s.b.x)
                }.max()
            }.max()
        }

        val minY: Int by lazy {
            wires.map { w ->
                w.segments.map { s ->
                    min(s.a.y, s.b.y)
                }.min()
            }.min()
        }

        val maxY: Int by lazy {
            wires.map { w ->
                w.segments.map { s ->
                    max(s.a.y, s.b.y)
                }.max()
            }.max()
        }

        private fun checkIntersectionAt(x: Int, y: Int): Boolean {
            val hWiresAtPoint = wires.filter { it.crossesHorizontallyAt(x, y) }
            val vWiresAtPoint = wires.filter { it.crossesVerticallyAt(x, y) }

            if (hWiresAtPoint.isNotEmpty() && vWiresAtPoint.isNotEmpty()
                && hWiresAtPoint != vWiresAtPoint
            ) {
                // wires cross this point both horizontally and vertically
                // and they're not the same one
                return true
            }

            return false
        }

        fun findIntersections(): List<Point> {
            return (minX..maxX).flatMap { x ->
                (minY..maxY).mapNotNull { y ->
                    if (checkIntersectionAt(x, y)) {
                        Point(x, y)
                    } else null
                }
            }.filterNot { it == Point(0, 0) }
        }

        fun findIntersectionsFast(): List<Point> {
            return wires.flatMap { w1 ->
                wires.flatMap { w2 ->
                    w1.intersectsAt(w2)
                }
            }
        }
    }

    private fun parseSegment(str: String, a: Point): Segment {
        val dir = str.first()
        val len = str.drop(1).toInt()

        val b = a + when (dir) {
            'R' -> Point(len, 0)
            'L' -> Point(-len, 0)
            'U' -> Point(0, -len)
            'D' -> Point(0, len)
            else -> throw IllegalArgumentException()
        }

        return Segment(a, b)
    }

    private fun parseWire(wireStr: String): Wire {
        val segments = wireStr.split(',')
            .fold(emptyList<Segment>()) { acc, segmentStr ->
                val lastPoint = acc.lastOrNull()?.b ?: Point(0, 0)
                val wire = parseSegment(segmentStr, lastPoint)
                acc + wire
            }

        return Wire(segments)
    }

    fun step1(): Long {
        val c = Circuit(input.map { parseWire(it) })
        val intersect = c.findIntersectionsFast()

        println(intersect.joinToString())

        // Day3Debug().display(c)

        return intersect.map { p -> p.distance(Point(0, 0)) }
            .min()
            .toLong()
    }

    fun step2(): Long {
        TODO("Not yet implemented")
    }
}