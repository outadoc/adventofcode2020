package fr.outadoc.aoc.twentytwentyfour

import org.jetbrains.kotlinx.dataframe.DataFrame
import org.jetbrains.kotlinx.dataframe.api.column
import org.jetbrains.kotlinx.dataframe.api.sort
import org.jetbrains.kotlinx.dataframe.io.readCSV
import kotlin.math.abs

class Day01 {

    private val first by column<Int>()
    private val second by column<Int>()

    fun run() {
        val df = DataFrame
            .readCSV(
                url = javaClass.getResource("/Day01.txt")!!,
                delimiter = '\t',
                header = listOf(
                    first.name(),
                    second.name()
                )
            )

        val firstCol = df[first].sort().toList()
        val secondCol = df[second].sort().toList()

        val sumOfDiffs = firstCol.zip(secondCol)
            .sumOf { (first, second) -> abs(second - first) }

        println("Part 1: $sumOfDiffs")

        val similarityScore =
            firstCol.sumOf { first ->
                // Find the number of times the current element appears in the second column
                val appearances = secondCol.count { second -> second == first }
                first * appearances
            }

        println("Part 2: $similarityScore")
    }
}
