fun readInput(filename: String): String {
    val year = "2020"
    return this::class.java.classLoader.let { loader ->
        (loader.getResourceAsStream("resources/$filename")
                ?: loader.getResourceAsStream("$year/resources/$filename"))
                .reader().readText()
    }
}

val input = readInput("day1.txt")
        .lineSequence()
        .map { it.toInt() }
        .toList()
        .toIntArray()

fun IntArray.findTwoSum(sum: Int): Int? {
    forEachIndexed { i, a ->
        val b = takeLast(size - i).find { b -> a + b == sum }
        if (b != null) {
            return a * b
        }
    }
    return null
}

fun IntArray.findThreeSum(sum: Int): Int? {
    forEachIndexed { i, a ->
        takeLast(size - i).forEachIndexed { j, b ->
            val c = takeLast(size - j)
                    .find { c -> a + b + c == sum }
            if (c != null) {
                return a * b * c
            }
        }
    }
    return null
}

input.sortedArray().findTwoSum(2020) // 786811
input.sortedArray().findThreeSum(2020) // 199068980
