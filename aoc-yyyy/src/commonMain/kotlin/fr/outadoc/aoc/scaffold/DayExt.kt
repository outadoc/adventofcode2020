package fr.outadoc.aoc.scaffold

internal fun <T, U : Day<T>> U.readDayInput() = readDayInput(year = "yyyy")
