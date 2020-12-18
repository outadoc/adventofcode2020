package fr.outadoc.aoc.twentytwenty

import kotlin.js.JsName
import kotlin.test.Test
import kotlin.test.assertEquals

class Day01Tests {

    @Test
    @JsName("testDay1Step1")
    fun `Test day 1 step 1`() {
        assertEquals(786811, Day01().step1())
    }

    @Test
    @JsName("testDay1Step2")
    fun `Test day 1 step 2`() {
        assertEquals(199068980, Day01().step2())
    }
}