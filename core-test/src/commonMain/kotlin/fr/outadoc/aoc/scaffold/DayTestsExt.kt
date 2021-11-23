package fr.outadoc.aoc.scaffold

import kotlin.test.assertEquals

fun <T, U : Day<T>> U.testStep1() = assertEquals(expectedStep1, step1())
fun <T, U : Day<T>> U.testStep2() = assertEquals(expectedStep2, step2())
