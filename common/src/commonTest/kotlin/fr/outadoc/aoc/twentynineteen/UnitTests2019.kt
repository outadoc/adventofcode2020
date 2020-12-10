package fr.outadoc.aoc.twentynineteen

import kotlin.js.JsName
import kotlin.test.Test
import kotlin.test.assertEquals

class UnitTests2019 {

    @Test
    @JsName("testDay1Step1")
    fun `Test day 1 step 1`() {
        assertEquals(3512133, Day1().step1())
    }

    @Test
    @JsName("testDay1Step2")
    fun `Test day 1 step 2`() {
        assertEquals(5265294, Day1().step2())
    }

    @Test
    @JsName("testDay2Step1")
    fun `Test day 2 step 1`() {
        assertEquals(4330636, Day2().step1())
    }

    @Test
    @JsName("testDay2Step2")
    fun `Test day 2 step 2`() {
        assertEquals(60086, Day2().step2())
    }
}