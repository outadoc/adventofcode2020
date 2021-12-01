package fr.outadoc.aoc.scaffold

interface Day<T> {

    fun step1(): T? = null
    fun step2(): T? = null

    val expectedStep1: T? get() = null
    val expectedStep2: T? get() = null
}
