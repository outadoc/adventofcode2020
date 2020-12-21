package fr.outadoc.aoc.twentytwenty

import fr.outadoc.aoc.scaffold.Day
import fr.outadoc.aoc.scaffold.Year

class Day01 : Day(Year.TwentyTwenty) {

    private val input: IntArray =
        readDayInput()
            .lineSequence()
            .map { it.toInt() }
            .toList()
            .toIntArray()

    private fun IntArray.findTwoSum(sum: Int): Int? {
        forEachIndexed { i, a ->
            val b = takeLast(size - i).find { b -> a + b == sum }
            if (b != null) {
                return a * b
            }
        }
        return null
    }

    private fun IntArray.findThreeSum(sum: Int): Int? {
        forEachIndexed { i, a ->
            takeLast(size - i).forEachIndexed { j, b ->
                val c = takeLast(size - j)
                    .find { c -> a + b + c == sum }
                if (c != null) {
                    return a * b * c
                }
            }
        }
        return null
    }

    fun step1(): Int {
        return input.sortedArray().findTwoSum(2020)!!
    }

    fun step2(): Int {
        return input.sortedArray().findThreeSum(2020)!!
    }
}