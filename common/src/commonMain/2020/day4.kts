fun readInput(filename: String): String {
    val year = "2020"
    return this::class.java.classLoader.let { loader ->
        (loader.getResourceAsStream("resources/$filename")
                ?: loader.getResourceAsStream("$year/resources/$filename"))
                .reader().readText()
    }
}

val input = readInput("day4.txt")

data class PassportCandidate(val properties: Map<String, String>)

fun String.parse(): List<PassportCandidate> {
    return split("\n\n")
            .filterNot { it.isEmpty() }
            .map { entry ->
                PassportCandidate(
                        properties = entry.split(' ', '\n')
                                .filterNot { it.isEmpty() }
                                .map { prop ->
                                    val components = prop.split(':')
                                    components[0] to components[1]
                                }.toMap()
                )
            }
}

val knownProperties = listOf("byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid", "cid")

val PassportCandidate.checkPropertiesPresent: Boolean
    get() {
        val mandatoryPassportProperties = knownProperties - "cid"
        val missingAnyMandatoryProperties = mandatoryPassportProperties.any { prop ->
            prop !in properties.keys
        }
        val hasAnyUnknownProperties = properties.any { prop ->
            prop.key !in knownProperties
        }
        return !missingAnyMandatoryProperties && !hasAnyUnknownProperties
    }

val PassportCandidate.checkPropertiesValid: Boolean
    get() = properties.all { prop ->
        when (prop.key) {
            "byr" -> prop.value.toInt() in 1920..2002
            "iyr" -> prop.value.toInt() in 2010..2020
            "eyr" -> prop.value.toInt() in 2020..2030
            "hgt" -> {
                val r = Regex("^([0-9]+)(in|cm)$")
                r.find(prop.value)?.groupValues?.let { groups ->
                    val height = groups[1].toInt()
                    val unit = groups[2]
                    when (unit) {
                        "in" -> height in 59..76
                        "cm" -> height in 150..193
                        else -> false
                    }
                } ?: false
            }
            "hcl" -> prop.value.matches(Regex("^#[0-9a-f]{6}$"))
            "ecl" -> prop.value in listOf("amb", "blu", "brn", "gry", "grn", "hzl", "oth")
            "pid" -> prop.value.length == 9 && prop.value.toIntOrNull() != null
            else -> true
        }
    }

fun step1(): Int {
    return input.parse().count { candidate ->
        candidate.checkPropertiesPresent
    }
}

fun step2(): Int {
    return input.parse().count { candidate ->
        candidate.checkPropertiesPresent && candidate.checkPropertiesValid
    }
}

step1() // 260

step2() // 153