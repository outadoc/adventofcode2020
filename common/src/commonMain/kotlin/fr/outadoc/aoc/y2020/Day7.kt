package fr.outadoc.aoc.y2020

import fr.outadoc.aoc.scaffold.Day
import fr.outadoc.aoc.scaffold.Year

class Day7 : Day(Year._2020) {

    private val bags = readDayInput()
            .lineSequence()
            .filterNot { it.isEmpty() }
            .parse()

    private val containerRegex = Regex("^([a-z ]+) bags contain .+$")
    private val contentsRegex = Regex(" ([0-9]+) ([a-z ]+) bags?[,.]")

    data class Bag(val bagColor: String, val contents: List<BagContent>)
    data class BagContent(val bagColor: String, val count: Int)

    private fun Sequence<String>.parse(): Sequence<Bag> {
        return map { rule ->
            val nameResult = containerRegex.find(rule)!!
            val contentsResult = contentsRegex.findAll(rule)
            Bag(
                    bagColor = nameResult.groupValues[1],
                    contents = contentsResult.map { res ->
                        BagContent(
                                bagColor = res.groupValues[2],
                                count = res.groupValues[1].toInt()
                        )
                    }.toList()
            )
        }
    }

    private val String.asBag: Bag
        get() = bags.first { it.bagColor == this }

    private fun Bag.contains(bagColor: String): Boolean {
        return when {
            contents.any { it.bagColor == bagColor } -> true
            else -> contents.any { contents ->
                contents.bagColor.asBag.contains(bagColor)
            }
        }
    }

    private val Bag.size: Long
        get() = 1 + contents.sumOf { content ->
            content.bagColor.asBag.size * content.count
        }

    override fun step1(): Long {
        return bags.count { bag -> bag.contains("shiny gold") }.toLong()
    }

    override fun step2(): Long {
        return "shiny gold".asBag.size - 1
    }
}