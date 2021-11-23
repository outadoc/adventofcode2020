package fr.outadoc.aoc.scaffold

interface Day<T> {

    fun step1(): T
    fun step2(): T

    val expectedStep1: T
    val expectedStep2: T
}
