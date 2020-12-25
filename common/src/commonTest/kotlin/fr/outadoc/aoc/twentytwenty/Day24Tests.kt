package fr.outadoc.aoc.twentytwenty

import kotlin.js.JsName
import kotlin.test.Test
import kotlin.test.assertEquals

class Day24Tests {

    @Test
    @JsName("testDay24Step1")
    fun `Test day 24 step 1`() {
        assertEquals(488, Day24().step1())
    }

    @Test
    @JsName("testDay24Step2")
    fun `Test day 24 step 2`() {
        assertEquals(null, Day24().step2())
    }
}