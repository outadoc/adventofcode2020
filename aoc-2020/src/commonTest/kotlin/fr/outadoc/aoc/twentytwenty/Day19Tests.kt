package fr.outadoc.aoc.twentytwenty

import kotlin.js.JsName
import kotlin.test.Test
import kotlin.test.assertEquals

class Day19Tests {

    @Test
    @JsName("testDay19Step1")
    fun `Test day 19 step 1`() {
        assertEquals(160, Day19().step1())
    }

    @Test
    @JsName("testDay19Step2")
    fun `Test day 19 step 2`() {
        assertEquals(357, Day19().step2())
    }
}