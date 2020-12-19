package fr.outadoc.aoc.twentytwenty

import fr.outadoc.aoc.scaffold.Day
import fr.outadoc.aoc.scaffold.Year

class Day19 : Day(Year.TwentyTwenty) {

    private val sections: List<String> =
        readDayInput().split("\n\n")

    private val step1Rules: Map<Int, String> =
        sections[0]
            .lines()
            .map { line ->
                line.split(": ")
                    .let { parts ->
                        parts[0].toInt() to parts[1]
                    }
            }.toMap()

    private val step2Rules: Map<Int, String> =
        step1Rules.mapValues { (key, rule) ->
            when (key) {
                8 -> "42 | 42 8"
                11 -> "42 31 | 42 11 31"
                else -> rule
            }
        }

    private val messages: List<String> =
        sections[1].lines()

    private fun String.ruleToPattern(rules: Map<Int, String>): String =
        when {
            contains("\"") -> replace("\"", "")
            else -> when (this) {
                // Special handling for modified rule 8, fuck it: repeating pattern
                "42 | 42 8" ->
                    "(${rules.getValue(42).ruleToPattern(rules)}+)"

                // Special handling for modified rule 11, fuck it:
                // right side and left side must repeat the same number of times
                "42 31 | 42 11 31" ->
                    (1..4).joinToString(separator = "|") { n ->
                        "${rules.getValue(42).ruleToPattern(rules)}{$n}" +
                            "${rules.getValue(31).ruleToPattern(rules)}{$n}"
                    }.let { pattern ->
                        "($pattern)"
                    }

                else -> split(" | ")
                    .joinToString(separator = "|") { orGroup ->
                        orGroup.split(" ")
                            .joinToString(separator = "") { ruleIndex ->
                                val rule = rules.getValue(ruleIndex.toInt())
                                rule.ruleToPattern(rules)
                            }
                    }.let { pattern ->
                        "($pattern)"
                    }
            }
        }

    private fun Map<Int, String>.getRule(pos: Int): Regex {
        val pattern = getValue(pos).ruleToPattern(this)
        return Regex(pattern = "^$pattern$")
    }

    override fun step1(): Long {
        val rule0 = step1Rules.getRule(0)
        return messages.count { message ->
            rule0.matches(message)
        }.toLong()
    }

    override fun step2(): Long {
        val rule0 = step2Rules.getRule(0)
        println(rule0)
        return messages.count { message ->
            rule0.matches(message)
        }.toLong()
    }
}