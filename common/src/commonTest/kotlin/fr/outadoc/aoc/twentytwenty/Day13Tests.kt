package fr.outadoc.aoc.twentytwenty

import kotlin.js.JsName
import kotlin.test.Test
import kotlin.test.assertEquals

class Day13Tests {

    @Test
    @JsName("testDay13Step1")
    fun `Test day 13 step 1`() {
        assertEquals(1895, Day13().step1())
    }

    @Test
    @JsName("testDay13Step2")
    fun `Test day 13 step 2`() {
        assertEquals(840493039281088, Day13().step2())
    }
}