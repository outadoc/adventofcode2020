package fr.outadoc.aoc.twentytwenty

import kotlin.js.JsName
import kotlin.test.Test
import kotlin.test.assertEquals

class Day05Tests {

    @Test
    @JsName("testDay5Step1")
    fun `Test day 5 step 1`() {
        assertEquals(915, Day05().step1())
    }

    @Test
    @JsName("testDay5Step2")
    fun `Test day 5 step 2`() {
        assertEquals(699, Day05().step2())
    }
}