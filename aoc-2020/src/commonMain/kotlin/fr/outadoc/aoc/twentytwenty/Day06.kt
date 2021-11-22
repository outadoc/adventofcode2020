package fr.outadoc.aoc.twentytwenty

import fr.outadoc.aoc.scaffold.Day
import fr.outadoc.aoc.scaffold.Year

class Day06 : Day(Year.TwentyTwenty) {

    private val input: List<Group> =
        readDayInput().parse()

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

    fun step1(): Int {
        return input.sumOf { group ->
            group.answeredYesByAnyone.size
        }
    }

    fun step2(): Int {
        return input.sumOf { group ->
            group.answeredYesByEverybody.size
        }
    }
}