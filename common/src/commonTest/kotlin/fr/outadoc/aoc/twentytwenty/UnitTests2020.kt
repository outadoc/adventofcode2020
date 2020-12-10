package fr.outadoc.aoc.twentytwenty

import kotlin.test.Test
import kotlin.test.assertEquals

class UnitTests2020 {

    @Test
    fun testDay1Step1() {
        assertEquals(786811, Day1().step1())
    }

    @Test
    fun testDay1Step2() {
        assertEquals(199068980, Day1().step2())
    }

    @Test
    fun testDay2Step1() {
        assertEquals(591, Day2().step1())
    }

    @Test
    fun testDay2Step2() {
        assertEquals(335, Day2().step2())
    }

    @Test
    fun testDay3Step1() {
        assertEquals(276, Day3().step1())
    }

    @Test
    fun testDay3Step2() {
        assertEquals(7812180000, Day3().step2())
    }

    @Test
    fun testDay4Step1() {
        assertEquals(260, Day4().step1())
    }

    @Test
    fun testDay4Step2() {
        assertEquals(153, Day4().step2())
    }

    @Test
    fun testDay5Step1() {
        assertEquals(915, Day5().step1())
    }

    @Test
    fun testDay5Step2() {
        assertEquals(699, Day5().step2())
    }

    @Test
    fun testDay6Step1() {
        assertEquals(6273, Day6().step1())
    }

    @Test
    fun testDay6Step2() {
        assertEquals(3254, Day6().step2())
    }

    @Test
    fun testDay7Step1() {
        assertEquals(332, Day7().step1())
    }

    @Test
    fun testDay7Step2() {
        assertEquals(10875, Day7().step2())
    }

    @Test
    fun testDay8Step1() {
        assertEquals(1528, Day8().step1())
    }

    @Test
    fun testDay8Step2() {
        assertEquals(640, Day8().step2())
    }

    @Test
    fun testDay9Step1() {
        assertEquals(22477624, Day9().step1())
    }

    @Test
    fun testDay9Step2() {
        assertEquals(2980044, Day9().step2())
    }

    @Test
    fun testDay10Step1() {
        assertEquals(1885, Day10().step1())
    }

    @Test
    fun testDay10Step2() {
        assertEquals(null, Day10().step2())
    }
}