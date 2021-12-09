package fr.outadoc.aoc.twentytwentyone

import fr.outadoc.aoc.scaffold.Day
import fr.outadoc.aoc.scaffold.product
import fr.outadoc.aoc.scaffold.readDayInput
import kotlin.jvm.JvmInline

class Day09 : Day<Int> {

    private companion object {
        const val MAX_HEIGHT = 9
    }

    private val heightMap: HeightMap =
        readDayInput()
            .lineSequence()
            .map { line -> line.toList().map { it.digitToInt() } }
            .toList()
            .let { HeightMap(it) }

    @JvmInline
    private value class HeightMap(val map: List<List<Int>>)

    private data class Position(val x: Int, val y: Int)

    private val Position.surrounding: Set<Position>
        get() = setOf(
            copy(y = y - 1),
            copy(y = y + 1),
            copy(x = x - 1),
            copy(x = x + 1)
        )

    private operator fun HeightMap.get(pos: Position): Int {
        return heightMap.map.getOrNull(pos.y)?.getOrNull(pos.x) ?: MAX_HEIGHT
    }

    private tailrec fun HeightMap.getBasinAt(knownBasin: Set<Position>): Set<Position> {
        val adjacent = knownBasin
            .flatMap { it.surrounding }
            .toSet()
            .minus(knownBasin)
            .filterNot { pos -> this[pos] == MAX_HEIGHT }
            .toSet()

        return when {
            adjacent.isEmpty() -> knownBasin
            else -> getBasinAt(knownBasin + adjacent)
        }
    }

    private fun HeightMap.findLowPoints(): List<Position> =
        map.flatMapIndexed { i, layer ->
            layer.mapIndexed { j, currentLocation ->
                Position(x = j, y = i).takeIf { currentPos ->
                    currentPos.surrounding
                        .map { pos -> heightMap[pos] }
                        .all { pos -> pos > currentLocation }
                }

            }.filterNotNull()
        }

    override fun step1() = heightMap.findLowPoints().sumOf { pos -> 1 + heightMap[pos] }

    override fun step2() =
        heightMap
            .findLowPoints()
            .map { lowPoint -> heightMap.getBasinAt(setOf(lowPoint)).size }
            .sortedDescending()
            .take(3)
            .product()

    override val expectedStep1 = 577
    override val expectedStep2 = 1_069_200
}
