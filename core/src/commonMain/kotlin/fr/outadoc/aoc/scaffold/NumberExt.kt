package fr.outadoc.aoc.scaffold

fun <T : Comparable<T>> Iterable<T>.max(): T = maxOrNull()!!
fun <T : Comparable<T>> Iterable<T>.min(): T = minOrNull()!!