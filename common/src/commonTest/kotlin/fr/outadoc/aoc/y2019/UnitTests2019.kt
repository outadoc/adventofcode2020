package fr.outadoc.aoc.y2019

import kotlin.test.Test
import kotlin.test.assertEquals

class UnitTests2019 {

    @Test
    fun testDay1Step1() {
        assertEquals(3512133, Day1().step1())
    }

    @Test
    fun testDay1Step2() {
        assertEquals(5265294, Day1().step2())
    }

    @Test
    fun testDay2Step1() {
        assertEquals(4330636, Day2().step1())
    }

    @Test
    fun testDay2Step2() {
        assertEquals(60086, Day2().step2())
    }
}