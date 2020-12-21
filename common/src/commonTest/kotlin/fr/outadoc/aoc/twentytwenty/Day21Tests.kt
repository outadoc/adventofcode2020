package fr.outadoc.aoc.twentytwenty

import kotlin.js.JsName
import kotlin.test.Test
import kotlin.test.assertEquals

class Day21Tests {

    @Test
    @JsName("testDay21Step1")
    fun `Test day 21 step 1`() {
        assertEquals(2573, Day21().step1())
    }

    @Test
    @JsName("testDay21Step2")
    fun `Test day 21 step 2`() {
        assertEquals(null, Day21().step2())
    }
}