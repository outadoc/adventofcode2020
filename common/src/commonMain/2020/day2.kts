fun readInput(filename: String): String {
    val year = "2020"
    return this::class.java.classLoader.let { loader ->
        (loader.getResourceAsStream("resources/$filename")
                ?: loader.getResourceAsStream("$year/resources/$filename"))
                .reader().readText()
    }
}

val input = readInput("day2.txt").lineSequence()

data class Policy(val first: Int, val second: Int, val letter: Char)
data class PasswordEntry(val policy: Policy, val password: String)

fun parse(input: Sequence<String>): Sequence<PasswordEntry> {
    val regex = Regex("^([0-9]+)-([0-9]+) ([a-z]): ([a-z0-9]+)$")
    return input.map { entry ->
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

fun step1(): Int {
    fun isValid(entry: PasswordEntry): Boolean {
            val letterCount = entry.password.count { c -> c == entry.policy.letter }
            return letterCount in entry.policy.first..entry.policy.second
        }

    return parse(input).count { entry -> isValid(entry) }
}

assert(step1() == 591)

fun step2(): Int {
    fun isValid(entry: PasswordEntry): Boolean {
        val isFirstLetterOk = entry.password[entry.policy.first - 1] == entry.policy.letter
        val isSecondLetterOk = entry.password[entry.policy.second - 1] == entry.policy.letter
        return isFirstLetterOk xor isSecondLetterOk
    }

    return parse(input).count { entry -> isValid(entry) }
}

step2() // 335