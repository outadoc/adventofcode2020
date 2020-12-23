package fr.outadoc.aoc.twentytwenty

import kotlin.js.JsName
import kotlin.test.Test
import kotlin.test.assertEquals

class Day22Tests {

    @Test
    @JsName("testDay22Step1")
    fun `Test day 22 step 1`() {
        assertEquals(32598, Day22().step1())
    }

    @Test
    @JsName("testDay22Step2")
    fun `Test day 22 step 2`() {
        assertEquals(35836, Day22().step2())
    }
}