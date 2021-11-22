package fr.outadoc.aoc.twentytwenty

import kotlin.js.JsName
import kotlin.test.Test
import kotlin.test.assertEquals

class Day09Tests {

    @Test
    @JsName("testDay9Step1")
    fun `Test day 9 step 1`() {
        assertEquals(22477624, Day09().step1())
    }

    @Test
    @JsName("testDay9Step2")
    fun `Test day 9 step 2`() {
        assertEquals(2980044, Day09().step2())
    }
}