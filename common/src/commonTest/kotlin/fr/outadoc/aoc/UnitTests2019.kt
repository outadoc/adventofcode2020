package fr.outadoc.aoc

import fr.outadoc.aoc.scaffold.Day
import fr.outadoc.aoc.y2019.*
import kotlin.test.Test
import kotlin.test.assertEquals

class UnitTests2019 {

    data class TestCase(val day: Day, val step1: Long? = null, val step2: Long? = null)

    private val testCases = listOf(
        TestCase(day = Day1(), step1 = 3512133, step2 = 5265294),
        TestCase(day = Day2(), step1 = 4330636, step2 = 60086),
    )

    @Test
    fun testAll2019Days() {
        testCases.forEach { test ->
            test.day.step1().let {
                assertEquals(
                    expected = test.step1,
                    actual = it,
                    message = "${test.day::class.simpleName} step 1"
                )
            }

            test.day.step2().let {
                assertEquals(
                    expected = test.step2,
                    actual = it,
                    message = "${test.day::class.simpleName} step 2"
                )
            }
        }
    }
}