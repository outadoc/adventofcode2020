package fr.outadoc.aoc.twentytwentyfour

import fr.outadoc.aoc.scaffold.Day
import fr.outadoc.aoc.scaffold.readDayInput
import org.jetbrains.kotlinx.dataframe.DataFrame
import org.jetbrains.kotlinx.dataframe.api.column
import org.jetbrains.kotlinx.dataframe.api.sort
import org.jetbrains.kotlinx.dataframe.io.readCSV
import kotlin.math.abs

class Day01 : Day<Int> {

    private val first by column<Int>()
    private val second by column<Int>()

    private val df = DataFrame
        .readCSV(
            stream = readDayInput().byteInputStream(),
            delimiter = '\t',
            header = listOf(
                first.name(),
                second.name()
            )
        )

    private val firstCol = df[first].sort().toList()
    private val secondCol = df[second].sort().toList()

    override fun step1(): Int {
        return firstCol.zip(secondCol)
            .sumOf { (first, second) -> abs(second - first) }
    }

    override val expectedStep1 = 2_285_373

    override fun step2(): Int {
        return firstCol.sumOf { first ->
            // Find the number of times the current element appears in the second column
            val appearances = secondCol.count { second -> second == first }
            first * appearances
        }
    }

    override val expectedStep2 = 21_142_653
}