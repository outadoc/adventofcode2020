package fr.outadoc.aoc.twentytwenty

import kotlin.js.JsName
import kotlin.test.Test
import kotlin.test.assertEquals

class Day06Tests {

    @Test
    @JsName("testDay6Step1")
    fun `Test day 6 step 1`() {
        assertEquals(6273, Day06().step1())
    }

    @Test
    @JsName("testDay6Step2")
    fun `Test day 6 step 2`() {
        assertEquals(3254, Day06().step2())
    }
}