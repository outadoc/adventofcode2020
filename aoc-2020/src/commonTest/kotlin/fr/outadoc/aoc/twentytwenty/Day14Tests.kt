package fr.outadoc.aoc.twentytwenty

import kotlin.js.JsName
import kotlin.test.Test
import kotlin.test.assertEquals

class Day14Tests {

    @Test
    @JsName("testDay14Step1")
    fun `Test day 14 step 1`() {
        assertEquals(13556564111697, Day14().step1())
    }

    @Test
    @JsName("testDay14Step2")
    fun `Test day 14 step 2`() {
        assertEquals(4173715962894, Day14().step2())
    }
}