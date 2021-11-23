package fr.outadoc.aoc.twentytwenty

import fr.outadoc.aoc.scaffold.Day
import fr.outadoc.aoc.scaffold.readDayInput

class Day06 : Day<Int> {

    private val input: List<Group> = readDayInput().parse()

    data class Person(val yesAnswers: Set<Char>)

    data class Group(val people: List<Person>) {
        val answeredYesByAnyone: Set<Char>
            get() = people.map { person ->
                person.yesAnswers
            }.flatten().toSet()

        val answeredYesByEverybody: Set<Char>
            get() = people.fold(answeredYesByAnyone) { acc, person ->
                acc.intersect(person.yesAnswers)
            }
    }

    private fun String.parse(): List<Group> {
        return split("\n\n").map { group ->
            Group(people = group.lines().map { person ->
                Person(yesAnswers = person.toSet())
            })
        }
    }

    override fun step1(): Int {
        return input.sumOf { group ->
            group.answeredYesByAnyone.size
        }
    }

    override fun step2(): Int {
        return input.sumOf { group ->
            group.answeredYesByEverybody.size
        }
    }

    override val expectedStep1: Int = 6273
    override val expectedStep2: Int = 3254
}