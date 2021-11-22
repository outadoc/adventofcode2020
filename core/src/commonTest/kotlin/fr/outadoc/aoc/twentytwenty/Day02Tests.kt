package fr.outadoc.aoc.twentytwenty

import kotlin.js.JsName
import kotlin.test.Test
import kotlin.test.assertEquals

class Day02Tests {

    @Test
    @JsName("testDay2Step1")
    fun `Test day 2 step 1`() {
        assertEquals(591, Day02().step1())
    }

    @Test
    @JsName("testDay2Step2")
    fun `Test day 2 step 2`() {
        assertEquals(335, Day02().step2())
    }
}