package fr.outadoc.aoc.twentytwenty

import kotlin.js.JsName
import kotlin.test.Test
import kotlin.test.assertEquals

class Day25Tests {

    @Test
    @JsName("testDay25Step1")
    fun `Test day 25 step 1`() {
        assertEquals(6198540, Day25().step1())
    }

    @Test
    @JsName("testDay25Step2")
    fun `Test day 25 step 2`() {
        assertEquals(null, Day25().step2())
    }
}