package fr.outadoc.aoc.twentytwentyone

import fr.outadoc.aoc.scaffold.Day
import fr.outadoc.aoc.scaffold.readDayInput
import kotlin.jvm.JvmInline

class Day13 : Day<String> {

    private sealed class Fold {
        data class Left(val x: Int) : Fold()
        data class Up(val y: Int) : Fold()
    }

    private data class Position(val x: Int, val y: Int)

    @JvmInline
    private value class State(val dots: Set<Position>) {

        override fun toString(): String {
            val xRange = dots.minOf { dot -> dot.x }..dots.maxOf { dot -> dot.x }
            val yRange = dots.minOf { dot -> dot.y }..dots.maxOf { dot -> dot.y }

            return buildString {
                yRange.forEach { y ->
                    xRange.forEach { x ->
                        val exists = Position(x = x, y = y) in dots
                        append(if (exists) "█" else " ")
                    }
                    appendLine()
                }
            }.trimEnd()
        }
    }

    private fun State.foldAlong(fold: Fold) = when (fold) {
        is Fold.Left -> State(
            dots.map { dot ->
                if (dot.x < fold.x) dot
                else dot.copy(x = (2 * fold.x) - dot.x)
            }.toSet()
        )
        is Fold.Up -> State(
            dots.map { dot ->
                if (dot.y < fold.y) dot
                else dot.copy(y = (2 * fold.y) - dot.y)
            }.toSet()
        )
    }

    private val input =
        readDayInput()
            .split("\n\n")
            .map { block -> block.lines() }
            .toList()

    private val dots =
        input.first()
            .map { dot ->
                val (x, y) = dot.split(',').map { it.toInt() }
                Position(x = x, y = y)
            }
            .toSet()

    private val reg = Regex("fold along ([a-z])=([0-9]+)")

    private val folds =
        input.last()
            .map { line ->
                val (axis, coordinate) = reg.find(line)!!.destructured
                when (axis) {
                    "y" -> Fold.Up(y = coordinate.toInt())
                    "x" -> Fold.Left(x = coordinate.toInt())
                    else -> error("parsing failed: $line")
                }
            }

    override fun step1() = folds.take(1)
        .fold(State(dots)) { acc, fold -> acc.foldAlong(fold) }
        .dots.count()
        .toString()

    override fun step2() = folds
        .fold(State(dots)) { acc, fold -> acc.foldAlong(fold) }
        .toString()

    override val expectedStep1 = "592"
    override val expectedStep2 = """
          ██  ██   ██    ██ ████ ████ █  █ █  █
           █ █  █ █  █    █ █    █    █ █  █  █
           █ █    █  █    █ ███  ███  ██   █  █
           █ █ ██ ████    █ █    █    █ █  █  █
        █  █ █  █ █  █ █  █ █    █    █ █  █  █
         ██   ███ █  █  ██  ████ █    █  █  ██
        """.trimIndent()
}
