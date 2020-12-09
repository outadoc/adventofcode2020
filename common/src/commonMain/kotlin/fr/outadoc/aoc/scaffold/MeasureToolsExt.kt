package fr.outadoc.aoc.scaffold

import kotlin.time.ExperimentalTime
import kotlin.time.measureTimedValue

@ExperimentalTime
fun <T : Any, U : Any> T.measure(block: T.() -> U): U {
    return measureTimedValue {
        block()
    }.let {
        println(it.duration.toString())
        it.value
    }
}