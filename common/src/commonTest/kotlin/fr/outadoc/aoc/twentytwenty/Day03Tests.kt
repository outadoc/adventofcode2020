package fr.outadoc.aoc.twentytwenty

import kotlin.js.JsName
import kotlin.test.Test
import kotlin.test.assertEquals

class Day03Tests {

    @Test
    @JsName("testDay3Step1")
    fun `Test day 3 step 1`() {
        assertEquals(276, Day03().step1())
    }

    @Test
    @JsName("testDay3Step2")
    fun `Test day 3 step 2`() {
        assertEquals(7812180000, Day03().step2())
    }
}