package fr.outadoc.aoc.twentytwenty

import kotlin.js.JsName
import kotlin.test.Test
import kotlin.test.assertEquals

class Day16Tests {

    @Test
    @JsName("testDay16Step1")
    fun `Test day 16 step 1`() {
        assertEquals(20231, Day16().step1())
    }

    @Test
    @JsName("testDay16Step2")
    fun `Test day 16 step 2`() {
        assertEquals(1940065747861, Day16().step2())
    }
}