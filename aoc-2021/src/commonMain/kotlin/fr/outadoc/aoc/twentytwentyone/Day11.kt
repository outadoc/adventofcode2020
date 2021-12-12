package fr.outadoc.aoc.twentytwentyone

import fr.outadoc.aoc.scaffold.Day
import fr.outadoc.aoc.scaffold.readDayInput

class Day11 : Day<Int> {

    private companion object {
        const val ENERGY_FLASH_THRESHOLD = 9
    }

    private data class Position(val x: Int, val y: Int)

    private val Position.surrounding: Set<Position>
        get() = (y - 1..y + 1).flatMap { y ->
            (x - 1..x + 1).map { x ->
                Position(x = x, y = y)
            }
        }.toSet()

    private val List<List<Int>>.flashingOctopusCount: Int
        get() = sumOf { line -> line.count { it == 0 } }

    private data class State(
        val energyMap: List<List<Int>>,
        val cumulativeFlashCount: Int = 0
    )

    private fun List<List<Int>>.reduce(): List<List<Int>> =
        incrementAll().flashRecurse().resetFlashingToZero()

    private fun List<List<Int>>.incrementAll(): List<List<Int>> =
        map { line -> line.map { energy -> energy + 1 } }

    private fun List<List<Int>>.resetFlashingToZero(): List<List<Int>> =
        map { line ->
            line.map { energy ->
                if (energy > ENERGY_FLASH_THRESHOLD) 0 else energy
            }
        }

    private tailrec fun List<List<Int>>.flashRecurse(
        flashedOctopuses: Set<Position> = emptySet()
    ): List<List<Int>> {
        val flashingOctopuses = flatMapIndexed { y, line ->
            line.mapIndexedNotNull { x, energy ->
                val pos = Position(x = x, y = y)
                if (energy > ENERGY_FLASH_THRESHOLD && pos !in flashedOctopuses) {
                    // FLASH
                    pos
                } else null
            }
        }

        if (flashingOctopuses.isEmpty()) return this

        val impacted: List<Position> =
            flashingOctopuses.flatMap { pos -> pos.surrounding }

        val newMap: List<List<Int>> =
            mapIndexed { y, line ->
                line.mapIndexed { x, energy ->
                    val pos = Position(x = x, y = y)
                    energy + impacted.count { impactedPos -> impactedPos == pos }
                }
            }

        return newMap.flashRecurse(
            flashedOctopuses = flashedOctopuses + flashingOctopuses
        )
    }

    private fun State.reduce(): State {
        val newMap = energyMap.reduce()
        return copy(
            energyMap = newMap,
            cumulativeFlashCount = cumulativeFlashCount + newMap.flashingOctopusCount
        )
    }

    private val initialState: State =
        readDayInput()
            .lineSequence()
            .map { line -> line.map { it.digitToInt() } }
            .let { map -> State(map.toList()) }

    private val octopusCount: Int =
        initialState.energyMap.size * initialState.energyMap.first().size

    override fun step1() = (0 until 100)
        .fold(initialState) { state, _ -> state.reduce() }
        .cumulativeFlashCount

    override fun step2(): Int {
        (0 until Int.MAX_VALUE)
            .fold(initialState) { state, step ->
                state.reduce().also { next ->
                    if (next.cumulativeFlashCount == state.cumulativeFlashCount + octopusCount) {
                        return step + 1
                    }
                }
            }

        error("now this one's tricky")
    }

    override val expectedStep1 = 1546
    override val expectedStep2 = 471
}
