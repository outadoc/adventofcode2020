package fr.outadoc.aoc.twentytwenty

import kotlin.js.JsName
import kotlin.test.Test
import kotlin.test.assertEquals

class Day18Tests {

    @Test
    @JsName("testDay18Step1")
    fun `Test day 18 step 1`() {
        assertEquals(209335026987, Day18().step1())
    }

    @Test
    @JsName("testDay18Step2")
    fun `Test day 18 step 2`() {
        assertEquals(33331817392479, Day18().step2())
    }
}