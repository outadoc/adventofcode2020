package fr.outadoc.aoc.twentytwenty

import kotlin.js.JsName
import kotlin.test.Test
import kotlin.test.assertEquals

class Day04Tests {

    @Test
    @JsName("testDay4Step1")
    fun `Test day 4 step 1`() {
        assertEquals(260, Day04().step1())
    }

    @Test
    @JsName("testDay4Step2")
    fun `Test day 4 step 2`() {
        assertEquals(153, Day04().step2())
    }
}