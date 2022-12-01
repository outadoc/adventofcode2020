package fr.outadoc.aoc.twentytwentytwo

import fr.outadoc.aoc.scaffold.Day
import fr.outadoc.aoc.scaffold.readDayInput

class Day01 : Day<Long> {

    private val input: Sequence<Elf> =
        readDayInput()
            .splitToSequence("\n\n")
            .map { elfInput ->
                Elf(
                    calories = elfInput.lines()
                        .map { caloriesStr ->
                            caloriesStr.toLong()
                        }
                )
            }

    private data class Elf(val calories: List<Long>) {
        val total: Long = calories.sum()
    }

    override fun step1(): Long {
        val elfWithMostCalories: Elf = input.maxBy { elf -> elf.total }
        return elfWithMostCalories.total
    }

    override val expectedStep1: Long = 67450

    override fun step2(): Long {
        val top3Elves: Sequence<Elf> =
            input.sortedByDescending { elf -> elf.total }.take(3)
        return top3Elves.sumOf { elf -> elf.total }
    }

    override val expectedStep2: Long = 199357
}
