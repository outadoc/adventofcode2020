package fr.outadoc.aoc.scaffold

import kotlin.time.ExperimentalTime
import kotlin.time.measureTimedValue

@ExperimentalTime
fun <T : Any, U : Any> T.measure(tag: String? = null, block: T.() -> U): U {
    return measureTimedValue {
        block()
    }.let {
        println("=== $tag: ${it.duration}")
        it.value
    }
}