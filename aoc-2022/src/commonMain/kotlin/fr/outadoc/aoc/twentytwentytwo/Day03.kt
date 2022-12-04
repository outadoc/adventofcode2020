package fr.outadoc.aoc.twentytwentytwo

import fr.outadoc.aoc.scaffold.Day
import fr.outadoc.aoc.scaffold.intersectAll
import fr.outadoc.aoc.scaffold.readDayInput
import kotlin.jvm.JvmInline

class Day03 : Day<Int> {

    @JvmInline
    private value class Item(val id: Char)

    private val allowedItems: List<Char> =
        ('a'..'z').toList() + ('A'..'Z').toList()

    private val Item.priority: Int
        get() = allowedItems.indexOf(id) + 1

    private data class Rucksack(
        val compartmentA: Set<Item>,
        val compartmentB: Set<Item>
    ) {
        val allItems = compartmentA + compartmentB
    }

    private val input: Sequence<Rucksack> =
        readDayInput()
            .lineSequence()
            .map { line -> line.toCharArray().map { c -> Item(c) } }
            .map { items ->
                val compartments: List<Set<Item>> = items.chunked(items.size / 2) { it.toSet() }
                Rucksack(
                    compartmentA = compartments[0],
                    compartmentB = compartments[1]
                )
            }

    private val Rucksack.commonItems: Set<Item>
        get() = compartmentA.intersect(compartmentB)

    override fun step1(): Int =
        input.sumOf { sack ->
            sack.commonItems.sumOf { item ->
                item.priority
            }
        }

    override val expectedStep1: Int = 7_831

    override fun step2(): Int =
        input.chunked(3)
            .map { group ->
                group.map { it.allItems }
                    .intersectAll()
                    .sumOf { item -> item.priority }
            }
            .also { println("set: ${it.toList()}") }
            .sum()

    override val expectedStep2: Int = 2_683
}
