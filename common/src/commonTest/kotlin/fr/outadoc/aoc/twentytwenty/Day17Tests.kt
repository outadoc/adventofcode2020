package fr.outadoc.aoc.twentytwenty

import kotlin.js.JsName
import kotlin.test.Test
import kotlin.test.assertEquals

class Day17Tests {

    @Test
    @JsName("testDay17Step1")
    fun `Test day 17 step 1`() {
        assertEquals(237, Day17().step1())
    }

    @Test
    @JsName("testDay17Step2")
    fun `Test day 17 step 2`() {
        assertEquals(2448, Day17().step2())
    }
}