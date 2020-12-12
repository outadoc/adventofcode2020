package fr.outadoc.aoc.twentytwenty

import fr.outadoc.aoc.scaffold.Day
import fr.outadoc.aoc.scaffold.Year

class Day02 : Day(Year.TwentyTwenty) {

    private val input: Sequence<PasswordEntry> =
        readDayInput()
            .lineSequence()
            .parse()

    data class Policy(val first: Int, val second: Int, val letter: Char)
    data class PasswordEntry(val policy: Policy, val password: String)

    private fun Sequence<String>.parse(): Sequence<PasswordEntry> {
        val regex = Regex("^([0-9]+)-([0-9]+) ([a-z]): ([a-z0-9]+)$")
        return map { entry ->
            val parsed = regex.find(entry)!!
            PasswordEntry(
                policy = Policy(
                    first = parsed.groupValues[1].toInt(),
                    second = parsed.groupValues[2].toInt(),
                    letter = parsed.groupValues[3].first()
                ),
                password = parsed.groupValues[4]
            )
        }
    }

    override fun step1(): Long {
        fun isValid(entry: PasswordEntry): Boolean {
            val letterCount = entry.password.count { c -> c == entry.policy.letter }
            return letterCount in entry.policy.first..entry.policy.second
        }

        return input.count { entry -> isValid(entry) }.toLong()
    }

    override fun step2(): Long {
        fun isValid(entry: PasswordEntry): Boolean {
            val isFirstLetterOk = entry.password[entry.policy.first - 1] == entry.policy.letter
            val isSecondLetterOk = entry.password[entry.policy.second - 1] == entry.policy.letter
            return isFirstLetterOk xor isSecondLetterOk
        }

        return input.count { entry -> isValid(entry) }.toLong()
    }
}
