package fr.outadoc.aoc.twentytwenty

import kotlin.js.JsName
import kotlin.test.Test
import kotlin.test.assertEquals

class UnitTests2020 {

    @Test
    @JsName("testDay1Step1")
    fun `Test day 1 step 1`() {
        assertEquals(786811, Day1().step1())
    }

    @Test
    @JsName("testDay1Step2")
    fun `Test day 1 step 2`() {
        assertEquals(199068980, Day1().step2())
    }

    @Test
    @JsName("testDay2Step1")
    fun `Test day 2 step 1`() {
        assertEquals(591, Day2().step1())
    }

    @Test
    @JsName("testDay2Step2")
    fun `Test day 2 step 2`() {
        assertEquals(335, Day2().step2())
    }

    @Test
    @JsName("testDay3Step1")
    fun `Test day 3 step 1`() {
        assertEquals(276, Day3().step1())
    }

    @Test
    @JsName("testDay3Step2")
    fun `Test day 3 step 2`() {
        assertEquals(7812180000, Day3().step2())
    }

    @Test
    @JsName("testDay4Step1")
    fun `Test day 4 step 1`() {
        assertEquals(260, Day4().step1())
    }

    @Test
    @JsName("testDay4Step2")
    fun `Test day 4 step 2`() {
        assertEquals(153, Day4().step2())
    }

    @Test
    @JsName("testDay5Step1")
    fun `Test day 5 step 1`() {
        assertEquals(915, Day5().step1())
    }

    @Test
    @JsName("testDay5Step2")
    fun `Test day 5 step 2`() {
        assertEquals(699, Day5().step2())
    }

    @Test
    @JsName("testDay6Step1")
    fun `Test day 6 step 1`() {
        assertEquals(6273, Day6().step1())
    }

    @Test
    @JsName("testDay6Step2")
    fun `Test day 6 step 2`() {
        assertEquals(3254, Day6().step2())
    }

    @Test
    @JsName("testDay7Step1")
    fun `Test day 7 step 1`() {
        assertEquals(332, Day7().step1())
    }

    @Test
    @JsName("testDay7Step2")
    fun `Test day 7 step 2`() {
        assertEquals(10875, Day7().step2())
    }

    @Test
    @JsName("testDay8Step1")
    fun `Test day 8 step 1`() {
        assertEquals(1528, Day8().step1())
    }

    @Test
    @JsName("testDay8Step2")
    fun `Test day 8 step 2`() {
        assertEquals(640, Day8().step2())
    }

    @Test
    @JsName("testDay9Step1")
    fun `Test day 9 step 1`() {
        assertEquals(22477624, Day9().step1())
    }

    @Test
    @JsName("testDay9Step2")
    fun `Test day 9 step 2`() {
        assertEquals(2980044, Day9().step2())
    }

    @Test
    @JsName("testDay10Step1")
    fun `Test day 10 step 1`() {
        assertEquals(1885, Day10().step1())
    }

    @Test
    @JsName("testDay10Step2")
    fun `Test day 10 step 2`() {
        assertEquals(2024782584832, Day10().step2())
    }
}