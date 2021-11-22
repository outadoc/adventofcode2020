package fr.outadoc.aoc.twentytwenty

import kotlin.js.JsName
import kotlin.test.Test
import kotlin.test.assertEquals

class Day12Tests {

    @Test
    @JsName("testDay12Step1")
    fun `Test day 12 step 1`() {
        assertEquals(1294, Day12().step1())
    }

    @Test
    @JsName("testDay12Step2")
    fun `Test day 12 step 2`() {
        assertEquals(20592, Day12().step2())
    }
}