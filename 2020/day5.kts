import kotlin.math.pow

fun readInput(filename: String): String {
    val year = "2020"
    return this::class.java.classLoader.let { loader ->
        (loader.getResourceAsStream("resources/$filename")
                ?: loader.getResourceAsStream("$year/resources/$filename"))
                .reader().readText()
    }
}

val input = readInput("day5.txt").lineSequence()

val ROW_CHAR_COUNT = 7
val COL_CHAR_COUNT = 3

val FRONT = 'F'
val BACK = 'B'
val RIGHT = 'R'
val LEFT = 'L'

val rowCount = 2f.pow(ROW_CHAR_COUNT).toInt()
val columnCount = 2f.pow(COL_CHAR_COUNT).toInt()

data class Seat(val row: Int, val col: Int, val id: Int = row * 8 + col)

fun String.parseSeat(): Seat {
    val rowCode = take(ROW_CHAR_COUNT)
    val colCode = takeLast(COL_CHAR_COUNT)
    return Seat(
            row = getPositionFromCode(rowCode, min = 0, max = rowCount - 1),
            col = getPositionFromCode(colCode, min = 0, max = columnCount - 1)
    )
}

tailrec fun getPositionFromCode(code: String, min: Int, max: Int): Int {
    // println("getPositionFromCode($code, $min, $max)")

    if (code.length == 1) {
        return when (code.first()) {
            // Lower half
            FRONT, LEFT -> min
            // Upper half
            BACK, RIGHT -> max
            else -> throw IllegalArgumentException()
        }
    }

    val (newMin, newMax) = when (code.first()) {
        // Lower half
        FRONT, LEFT -> min to min + ((max - min) / 2)
        // Upper half
        BACK, RIGHT -> min + ((max - min) / 2) + 1 to max
        else -> throw IllegalArgumentException()
    }

    return getPositionFromCode(code.drop(1), newMin, newMax)
}

fun step1(): Int {
    return input
            .map { it.parseSeat() }
            .maxOf { it.id }
}

step1() // 915

fun step2(): Int {
    val allSeats = (0 until rowCount).map { row ->
        (0 until columnCount).map { col ->
            Seat(row, col)
        }
    }.flatten()

    val registeredSeats = input.map { it.parseSeat() }

    val minId = registeredSeats.minOf { it.id }
    val maxId = registeredSeats.maxOf { it.id }

    val notRegistered = allSeats.filterNot { seat ->
        seat in registeredSeats
    }.filter { seat ->
        seat.id in (minId..maxId)
    }

    return notRegistered.first().id
}

step2() // 699