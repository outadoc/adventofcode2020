package fr.outadoc.aoc.twentytwenty

import kotlin.js.JsName
import kotlin.test.Test
import kotlin.test.assertEquals

class Day11Tests {

    @Test
    @JsName("testDay11Step1")
    fun `Test day 11 step 1`() {
        assertEquals(2324, Day11().step1())
    }

    @Test
    @JsName("testDay11Step2")
    fun `Test day 11 step 2`() {
        assertEquals(2068, Day11().step2())
    }
}