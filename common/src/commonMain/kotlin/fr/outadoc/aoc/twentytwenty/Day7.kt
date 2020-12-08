package fr.outadoc.aoc.twentytwenty

import fr.outadoc.aoc.scaffold.Day
import fr.outadoc.aoc.scaffold.Year

class Day7 : Day(Year.TwentyTwenty) {

    private val containerRegex = Regex("^([a-z ]+) bags contain .+$")
    private val contentsRegex = Regex(" ([0-9]+) ([a-z ]+) bags?[,.]")

    private val rules = readDayInput()
        .lineSequence()
        .parse()

    data class Rule(val color: String, val contents: List<Content>) {
        data class Content(val count: Int, val color: String)
    }

    private fun Sequence<String>.parse(): Sequence<Rule> = map { rule ->
        val nameResult = containerRegex.find(rule)!!
        val contentsResult = contentsRegex.findAll(rule)

        Rule(
            color = nameResult.groupValues[1],
            contents = contentsResult.map { res ->
                Rule.Content(
                    count = res.groupValues[1].toInt(),
                    color = res.groupValues[2]
                )
            }.toList()
        )
    }

    data class Bag(val color: String, var contents: List<Content> = emptyList()) {
        data class Content(val count: Int, val bag: Bag)

        val size: Long
            get() = 1 + contents.sumOf { (count, bag) ->
                count * bag.size
            }

        fun contains(bag: Bag): Boolean = when {
            contents.any { (_, containedBag) -> containedBag == bag } -> true
            else -> contents.any { (_, containedBag) ->
                containedBag.contains(bag)
            }
        }
    }

    private val bags = rules.map { rule ->
        rule.color to Bag(color = rule.color)
    }.toMap()

    private fun bagByColor(color: String): Bag = bags[color]!!

    init {
        // Initialize bag contents
        rules.forEach { rule ->
            bagByColor(rule.color).apply {
                contents = rule.contents.map { (count, color) ->
                    Bag.Content(
                        count = count,
                        bag = bagByColor(color)
                    )
                }
            }
        }
    }

    override fun step1(): Long {
        val target = bagByColor("shiny gold")
        return bags.values.count { bag -> bag.contains(target) }.toLong()
    }

    override fun step2(): Long {
        val target = bagByColor("shiny gold")
        return target.size - 1
    }
}