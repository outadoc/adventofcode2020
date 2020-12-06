package fr.outadoc.aoc.y2020

import fr.outadoc.aoc.scaffold.Day
import fr.outadoc.aoc.scaffold.Year
import fr.outadoc.aoc.scaffold.toSortedSetCommon

class Day6 : Day(Year._2020) {

    private val input = readDayInput()

    data class Person(val yesAnswers: List<Char>)

    data class Group(val people: List<Person>) {
        val answeredYesByAnyone: Set<Char>
            get() = people.map { person ->
                person.yesAnswers
            }.flatten().toSortedSetCommon()

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
        val parsed = input.parse()
        return parsed.sumBy { group ->
            group.answeredYesByAnyone.size
        }.toLong()
    }

    override fun step2(): Long {
        val parsed = input.parse()
        return parsed.sumBy { group ->
            group.answeredYesByEverybody.size
        }.toLong()
    }
}