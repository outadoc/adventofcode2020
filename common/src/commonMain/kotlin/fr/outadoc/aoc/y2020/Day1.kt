package fr.outadoc.aoc.y2020

import fr.outadoc.aoc.scaffold.Day
import fr.outadoc.aoc.scaffold.Year

class Day1 : Day(Year._2020) {

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

    override fun step1(): Long {
        return input.sortedArray().findTwoSum(2020)!!.toLong()
    }

    override fun step2(): Long {
        return input.sortedArray().findThreeSum(2020)!!.toLong()
    }
}