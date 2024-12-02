package fr.outadoc.aoc.twentytwentyfour

import fr.outadoc.aoc.scaffold.Day
import fr.outadoc.aoc.scaffold.readDayInput
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.toPersistentList
import kotlin.math.abs

class Day02 : Day<Int> {

    private val input =
        readDayInput()
            .lineSequence()
            .map { report ->
                report.split(" ")
                    .map { level -> level.toInt() }
                    .toPersistentList()
            }

    override fun step1(): Int {
        return input.count { report ->
            report.isSafe(dampen = false)
        }
    }

    override val expectedStep1 = 306

    override fun step2(): Int {
        return input.count { report ->
            report.isSafe(dampen = true)
        }
    }

    override val expectedStep2 = null

    private fun PersistentList<Int>.isSafe(dampen: Boolean): Boolean {
        val differences: List<Int> = this
            .windowed(2)
            .map { (a, b) -> b - a }

        val firstIncreasingLevel = differences.indexOfFirst { it > 0 }
        val firstDecreasingLevel = differences.indexOfFirst { it < 0 }
        val firstStagnantLevel = differences.indexOfFirst { it == 0 }

        if (firstIncreasingLevel == 0 && firstDecreasingLevel >= 0) {
            if (dampen) {
                println("DAMPENING!")
                return this.removeAt(firstDecreasingLevel).isSafe(dampen = false)
            }

            println("$this: Unsafe because not all levels are increasing: $differences")
            return false
        }

        if (firstDecreasingLevel == 0 && firstIncreasingLevel >= 0) {
            if (dampen) {
                println("DAMPENING!")
                return this.removeAt(firstDecreasingLevel).isSafe(dampen = false)
            }

            println("$this: Unsafe because not all levels are decreasing: $differences")
            return false
        }

        if (firstStagnantLevel >= 0) {
            if (dampen) {
                println("DAMPENING!")
                return this.removeAt(firstStagnantLevel).isSafe(dampen = false)
            }

            println("$this: Unsafe because a level is stagnant: $differences")
            return false
        }

        val levelTooHighIndex = differences.indexOfFirst { level -> abs(level) > 3 }
        if (levelTooHighIndex >= 0) {
            if (dampen) {
                println("DAMPENING!")
                return this.removeAt(levelTooHighIndex + 1).isSafe(dampen = false)
            }

            println("$this: Unsafe because some levels increased/decreased by more than 3: $differences")
            return false
        }

        println("$this: Safe")

        return true
    }
}