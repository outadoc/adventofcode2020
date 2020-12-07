package fr.outadoc.aoc.y2020

import fr.outadoc.aoc.scaffold.Day
import fr.outadoc.aoc.scaffold.Year

class Day7 : Day(Year._2020) {

    private val containerRegex = Regex("^([a-z ]+) bags contain .+$")
    private val contentsRegex = Regex(" ([0-9]+) ([a-z ]+) bags?[,.]")

    private val rules = readDayInput()
        .lineSequence()
        .filterNot { it.isEmpty() }
        .parse()

    private fun Sequence<String>.parse(): Sequence<Rule> {
        return map { rule ->
            val nameResult = containerRegex.find(rule)!!
            val contentsResult = contentsRegex.findAll(rule)
            Rule(
                bagName = nameResult.groupValues[1],
                contents = contentsResult.map { res ->
                    res.groupValues[1].toInt() to res.groupValues[2]
                }.toList()
            )
        }
    }

    data class Rule(val bagName: String, val contents: List<Pair<Int, String>>)
    data class Bag(val bagName: String, var contents: List<Pair<Int, Bag>>) {

        val size: Long
            get() = 1 + contents.sumOf { (count, bag) ->
                count * bag.size
            }
    }

    private val bagMap = rules.map { rule ->
        rule.bagName to Bag(rule.bagName, emptyList())
    }.toMap()

    private fun bagByName(name: String): Bag = bagMap[name]!!

    init {
        // Initialize bag contents
        rules.forEach { rule ->
            bagByName(rule.bagName).apply {
                contents = rule.contents.map { (count, bagName) ->
                    count to bagByName(bagName)
                }
            }
        }
    }

    private val containsCacheMap = mutableMapOf<Pair<Bag, Bag>, Boolean>()

    private fun Bag.contains(bag: Bag): Boolean {
        val cachedValue = containsCacheMap[this to bag]
        return when {
            cachedValue != null -> cachedValue
            contents.any { (_, containedBag) -> containedBag == bag } -> true
            else -> contents.any { (_, containedBag) ->
                containedBag.contains(bag)
            }
        }.also { containsColor ->
            containsCacheMap[this to bag] = containsColor
        }
    }

    override fun step1(): Long {
        val target = bagByName("shiny gold")
        return bagMap.values.count { bag -> bag.contains(target) }.toLong()
    }

    override fun step2(): Long {
        val target = bagByName("shiny gold")
        return target.size - 1
    }
}