package fr.outadoc.aoc.scaffold

actual fun <T : Comparable<T>> Iterable<T>.toSortedSetCommon(): Set<T> = toSortedSet()