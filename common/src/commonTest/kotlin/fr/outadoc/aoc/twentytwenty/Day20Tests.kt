package fr.outadoc.aoc.twentytwenty

import kotlin.js.JsName
import kotlin.test.Test
import kotlin.test.assertEquals

class Day20Tests {

    @Test
    @JsName("testDay20Step1")
    fun `Test day 20 step 1`() {
        assertEquals(14986175499719, Day20().step1())
    }

    @Test
    @JsName("testDay20Step2")
    fun `Test day 20 step 2`() {
        assertEquals(null, Day20().step2())
    }
}