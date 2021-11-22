package fr.outadoc.aoc.twentytwenty

import kotlin.js.JsName
import kotlin.test.Test
import kotlin.test.assertEquals

class Day10Tests {

    @Test
    @JsName("testDay10Step1")
    fun `Test day 10 step 1`() {
        assertEquals(1885, Day10().step1())
    }

    @Test
    @JsName("testDay10Step2")
    fun `Test day 10 step 2`() {
        assertEquals(2024782584832, Day10().step2())
    }
}