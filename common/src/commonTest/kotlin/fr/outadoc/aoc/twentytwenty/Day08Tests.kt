package fr.outadoc.aoc.twentytwenty

import kotlin.js.JsName
import kotlin.test.Test
import kotlin.test.assertEquals

class Day08Tests {

    @Test
    @JsName("testDay8Step1")
    fun `Test day 8 step 1`() {
        assertEquals(1528, Day08().step1())
    }

    @Test
    @JsName("testDay8Step2")
    fun `Test day 8 step 2`() {
        assertEquals(640, Day08().step2())
    }
}