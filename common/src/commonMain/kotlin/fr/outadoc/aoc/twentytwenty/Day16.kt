package fr.outadoc.aoc.twentytwenty

import fr.outadoc.aoc.scaffold.Day
import fr.outadoc.aoc.scaffold.Year

class Day16 : Day(Year.TwentyTwenty) {

    companion object {
        private val ruleRegex = Regex("^([a-z ]+): ([0-9]+)-([0-9]+) or ([0-9]+)-([0-9]+)$")
    }

    private data class Rule(val fieldName: String, val validRanges: List<LongRange>)

    private fun Rule.isValueValid(value: Long): Boolean {
        return validRanges.any { range -> range.contains(value) }
    }

    private val sections: List<String> =
        readDayInput().split("\n\n")

    private val rules: List<Rule> =
        sections[0]
            .lines()
            .map { rule ->
                val groups = ruleRegex.find(rule)!!.groupValues
                Rule(
                    fieldName = groups[1],
                    validRanges = listOf(
                        groups[2].toLong()..groups[3].toLong(),
                        groups[4].toLong()..groups[5].toLong()
                    )
                )
            }

    private fun String.parseTicketValues(): List<Long> =
        split(',').map { it.toLong() }

    private val myTicket: List<Long> =
        sections[1]
            .lines()
            .drop(1)
            .map { ticket -> ticket.parseTicketValues() }
            .first()

    private val nearbyTickets: List<List<Long>> =
        sections[2]
            .lines()
            .drop(1)
            .map { ticket -> ticket.parseTicketValues() }

    private fun mapFieldIndexesToRules(rules: List<Rule>, tickets: List<List<Long>>): Map<Int, Rule> {
        val fieldIndices = rules.indices

        // Map each index to the list of rules that could fit it
        val validRulesForIndices = fieldIndices.map { index ->
            val rulesForIndex = rules.filter { rule ->
                tickets.all { ticketValues ->
                    rule.isValueValid(ticketValues[index])
                }
            }
            index to rulesForIndex
        }

        // Sort the list by the number of rules that matches each index.
        // Helpfully, there's always n+1 valid rule for each new element,
        // so we can just check which element didn't exist yet in the accumulator.
        return validRulesForIndices
            .sortedBy { (_, rules) -> rules.size }
            .fold(emptyMap()) { acc: Map<Int, Rule>, (index: Int, possibleRules: List<Rule>) ->
                acc + (index to possibleRules.first { rule -> rule !in acc.values })
            }
    }

    private data class Field(val name: String, val value: Long)
    private data class Ticket(val fields: List<Field>)

    private fun List<Long>.parseTicket(): Ticket {
        val validTickets: List<List<Long>> =
            nearbyTickets.filter { ticket ->
                ticket.all { value ->
                    rules.any { rule -> rule.isValueValid(value) }
                }
            }.plusElement(this)

        val validRuleIndexes = mapFieldIndexesToRules(rules, validTickets)

        val myTicketRules: List<Pair<Rule, Long>> =
            this.mapIndexed { index, value ->
                validRuleIndexes.getValue(index) to value
            }

        return Ticket(
            fields = myTicketRules.map { (rule, value) ->
                Field(name = rule.fieldName, value = value)
            }
        )
    }

    override fun step1(): Long {
        return nearbyTickets.flatMap { ticket ->
            ticket.filterNot { value ->
                // Filter out all valid values
                rules.any { rule -> rule.isValueValid(value) }
            }
        }.sum()
    }

    override fun step2(): Long {
        return myTicket.parseTicket()
            .fields
            .filter { field -> field.name.startsWith("departure") }
            .fold(1) { acc, field -> acc * field.value }
    }
}