fun readInput(filename: String): String {
    val year = "2020"
    return this::class.java.classLoader.let { loader ->
        (loader.getResourceAsStream("resources/$filename")
                ?: loader.getResourceAsStream("$year/resources/$filename"))
                .reader().readText()
    }
}

val input = readInput("day3.txt")

val map: Array<CharArray> = input
        .lines()
        .map { line -> line.toCharArray() }
        .toTypedArray()

val Array<CharArray>.height: Int
    get() = size

val Array<CharArray>.width: Int
    get() = this[0].size

val TREE = '#'

fun Array<CharArray>.getPoint(x: Int, y: Int): Char {
    return this[y].get(x % width)
}

fun Array<CharArray>.getNumberOfTreesOnSlope(slope: Pair<Int, Int>): Int {
    var ix = 0
    var iy = 0
    var treeCount = 0

    while (iy < map.height) {
        if (map.getPoint(ix, iy) == TREE) {
            treeCount += 1
        }
        ix += slope.first
        iy += slope.second
    }

    return treeCount
}

fun step1(): Int {
    val slope = 3 to 1
    return map.getNumberOfTreesOnSlope(slope)
}

step1() // 276

fun step2(): Long {
    val slopes = listOf(
            1 to 1,
            3 to 1,
            5 to 1,
            7 to 1,
            1 to 2
    )

    return slopes.fold(1) { acc, slope ->
        acc * map.getNumberOfTreesOnSlope(slope)
    }
}

step2() // 7812180000