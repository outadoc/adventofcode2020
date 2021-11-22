package fr.outadoc.aoc.twentytwenty

import kotlin.js.JsName
import kotlin.test.Test
import kotlin.test.assertEquals

class Day07Tests {

    @Test
    @JsName("testDay7Step1")
    fun `Test day 7 step 1`() {
        assertEquals(332, Day07().step1())
    }

    @Test
    @JsName("testDay7Step2")
    fun `Test day 7 step 2`() {
        assertEquals(10875, Day07().step2())
    }
}