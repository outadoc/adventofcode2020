package fr.outadoc.aoc.twentytwenty

import kotlin.js.JsName
import kotlin.test.Test
import kotlin.test.assertEquals

class Day15Tests {

    @Test
    @JsName("testDay15Step1")
    fun `Test day 15 step 1`() {
        assertEquals(1280, Day15().step1())
    }

    @Test
    @JsName("testDay15Step2")
    fun `Test day 15 step 2`() {
        assertEquals(651639, Day15().step2())
    }
}