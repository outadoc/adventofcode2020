package fr.outadoc.aoc.scaffold

expect fun <T : Comparable<T>> Iterable<T>.toSortedSetCommon(): Set<T>