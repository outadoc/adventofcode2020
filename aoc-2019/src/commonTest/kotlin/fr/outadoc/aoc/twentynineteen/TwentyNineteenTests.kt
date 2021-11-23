package fr.outadoc.aoc.twentynineteen

import fr.outadoc.aoc.scaffold.testStep1
import fr.outadoc.aoc.scaffold.testStep2
import kotlin.js.JsName
import kotlin.test.Test

class TwentyNineteenTests {
    @Test @JsName("testDay01Step1") fun `Day 01 step 1`() = Day01().testStep1()
    @Test @JsName("testDay01Step2") fun `Day 01 step 2`() = Day01().testStep2()
    @Test @JsName("testDay02Step1") fun `Day 02 step 1`() = Day02().testStep1()
    @Test @JsName("testDay02Step2") fun `Day 02 step 2`() = Day02().testStep2()
    @Test @JsName("testDay03Step1") fun `Day 03 step 1`() = Day03().testStep1()
    @Test @JsName("testDay03Step2") fun `Day 03 step 2`() = Day03().testStep2()
}
