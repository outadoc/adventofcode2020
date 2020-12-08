package fr.outadoc.aoc.twentytwenty

import fr.outadoc.aoc.scaffold.Day
import fr.outadoc.aoc.scaffold.Year

class Day6 : Day(Year.TwentyTwenty) {

    private val input: List<Group> =
        readDayInput().parse()

    data class Person(val yesAnswers: List<Char>)

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
                Person(yesAnswers = person.toList())
            })
        }
    }

    override fun step1(): Long {
        return input.sumBy { group ->
            group.answeredYesByAnyone.size
        }.toLong()
    }

    override fun step2(): Long {
        return input.sumBy { group ->
            group.answeredYesByEverybody.size
        }.toLong()
    }
}