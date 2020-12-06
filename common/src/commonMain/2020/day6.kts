fun readInput(filename: String): String {
    val year = "2020"
    return this::class.java.classLoader.let { loader ->
        (loader.getResourceAsStream("resources/$filename")
                ?: loader.getResourceAsStream("$year/resources/$filename"))
                .reader().readText()
    }
}

val input = readInput("day6.txt")

data class Person(val yesAnswers: List<Char>)

data class Group(val people: List<Person>) {
    val answeredYesByAnyone: Set<Char>
        get() = people.map { person ->
            person.yesAnswers
        }.flatten().toSortedSet()

    val answeredYesByEverybody: Set<Char>
        get() = people.fold(answeredYesByAnyone) { acc, person ->
            acc.intersect(person.yesAnswers)
        }
}

fun String.parse(): List<Group> {
    return split("\n\n").map { group ->
        Group(people = group.lines().map { person ->
            Person(yesAnswers = person.toList())
        })
    }
}

fun step1(): Int {
    val parsed = input.parse()
    return parsed.sumBy { group ->
        group.answeredYesByAnyone.size
    }
}

step1() // 6273

fun step2(): Int {
    val parsed = input.parse()
    return parsed.sumBy { group ->
        group.answeredYesByEverybody.size
    }
}

step2() // 3254