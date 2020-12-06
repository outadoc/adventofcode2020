package fr.outadoc.aoc.y2020

import fr.outadoc.aoc.scaffold.Day
import kotlin.test.Test
import kotlin.test.assertEquals

class UnitTests2020 {

    data class TestCase(val day: Day, val step1: Long? = null, val step2: Long? = null)

    private val testCases = listOf(
        TestCase(day = Day1(), step1 = 786811, step2 = 199068980),
        TestCase(day = Day2(), step1 = 591, step2 = 335),
        TestCase(day = Day3(), step1 = 276, step2 = 7812180000),
        TestCase(day = Day4(), step1 = 260, step2 = 153),
        TestCase(day = Day5(), step1 = 915, step2 = 699),
        TestCase(day = Day6(), step1 = 6273, step2 = 3254),
    )

    @Test
    fun testAll2020Days() {
        testCases.forEach { test ->
            test.day.step1().let {
                assertEquals(
                    expected = test.step1,
                    actual = it,
                    message = "${test.day::class.simpleName} step 1 result was $it, but expected ${test.step1}"
                )
            }

            test.day.step2().let {
                assertEquals(
                    expected = test.step2,
                    actual = it,
                    message = "${test.day::class.simpleName} step 2 result was $it, but expected ${test.step2}"
                )
            }
        }
    }
}