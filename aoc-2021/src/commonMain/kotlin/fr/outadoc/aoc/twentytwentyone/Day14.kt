package fr.outadoc.aoc.twentytwentyone

import fr.outadoc.aoc.scaffold.Day
import fr.outadoc.aoc.scaffold.readDayInput

class Day14 : Day<Long> {

    private val input = readDayInput()
        .split("\n\n")
        .map { block -> block.lines() }

    private val template = input.first().first().toList()

    private val rules = input.last()
        .associate { rule ->
            val (from, to) = rule.split(" -> ")
            from.toList() to to.first()
        }

    private data class State(
        val pairFrequencies: Map<List<Char>, Long>,
        val charFrequencies: Map<Char, Long>
    )

    private val initialState = State(
        pairFrequencies = template.windowed(size = 2).frequencyMap(),
        charFrequencies = template.frequencyMap()
    )

    private fun State.reduce(): State {
        val pairFrequenciesNext = pairFrequencies.toMutableMap()
        val charFrequenciesNext = charFrequencies.toMutableMap()

        pairFrequencies
            .filterValues { count -> count > 0 }
            .forEach { (pair, count) ->
                val (left, right) = pair
                rules[pair]?.let { replacement ->
                    with(pairFrequenciesNext) {
                        set(pair, getValue(pair) - count)
                        set(listOf(left, replacement), getOrElse(listOf(left, replacement)) { 0L } + count)
                        set(listOf(replacement, right), getOrElse(listOf(replacement, right)) { 0L } + count)
                    }

                    with(charFrequenciesNext) {
                        set(replacement, getOrElse(replacement) { 0L } + count)
                    }
                }
            }

        return copy(
            pairFrequencies = pairFrequenciesNext.filterValues { count -> count > 0 },
            charFrequencies = charFrequenciesNext
        )
    }

    private fun <T> List<T>.frequencyMap(): Map<T, Long> =
        groupingBy { it }
            .eachCount()
            .map { (window, count) -> window to count.toLong() }
            .toMap()

    private fun <T> Map<T, Long>.minMaxDifference(): Long =
        maxOf { (_, count) -> count } - minOf { (_, count) -> count }

    override fun step1() = (0 until 10)
        .fold(initialState) { acc, _ -> acc.reduce() }
        .charFrequencies
        .minMaxDifference()

    override fun step2() = (0 until 40)
        .fold(initialState) { acc, _ -> acc.reduce() }
        .charFrequencies
        .minMaxDifference()

    override val expectedStep1 = 2010L
    override val expectedStep2 = 2437698971143L
}
