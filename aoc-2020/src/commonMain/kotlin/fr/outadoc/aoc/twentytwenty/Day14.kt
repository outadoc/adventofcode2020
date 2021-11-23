package fr.outadoc.aoc.twentytwenty

import fr.outadoc.aoc.scaffold.Day
import fr.outadoc.aoc.scaffold.readDayInput

class Day14 : Day {

    companion object {
        private val maskRegex = Regex("^mask = ([01X]+)$")
        private val setRegex = Regex("^mem\\[([0-9]+)] = ([0-9]+)$")
    }

    private val actions: Sequence<Action> =
        readDayInput()
            .lineSequence()
            .map { line ->
                val maskRes = maskRegex.find(line)
                val setRes = setRegex.find(line)

                when {
                    maskRes != null -> Action.SetMask(maskRes.groupValues[1])
                    setRes != null -> Action.SetValue(
                        addr = setRes.groupValues[1].toLong(),
                        value = setRes.groupValues[2].toLong()
                    )
                    else -> throw IllegalArgumentException()
                }
            }

    private data class Mask(val mask: String) {

        private fun Long.setBit(n: Int): Long = this or (1L shl n)
        private fun Long.clearBit(n: Int): Long = this and (1L shl n).inv()

        private val reverseMask: String = mask.reversed()

        fun maskValue(value: Long): Long =
            reverseMask.foldIndexed(value) { n: Int, acc: Long, bit: Char ->
                when (bit) {
                    '0' -> acc.clearBit(n)
                    '1' -> acc.setBit(n)
                    else -> acc
                }
            }

        fun maskAddress(addr: Long): List<Long> =
            reverseMask.foldIndexed(listOf(addr)) { n: Int, acc: List<Long>, bit: Char ->
                when (bit) {
                    '0' -> acc
                    '1' -> acc.map { addr -> addr.setBit(n) }
                    else -> acc.map { addr ->
                        listOf(
                            addr.setBit(n),
                            addr.clearBit(n)
                        )
                    }.flatten()
                }
            }
    }

    private data class State(
        val mask: Mask = Mask(""),
        val memory: Map<Long, Long> = mapOf()
    )

    private sealed class Action {
        data class SetMask(val mask: String) : Action()
        data class SetValue(val addr: Long, val value: Long) : Action()
    }

    private fun State.reduceV1(action: Action): State = when (action) {
        is Action.SetMask -> copy(mask = Mask(action.mask))
        is Action.SetValue -> copy(memory = memory.toMutableMap().apply {
            put(action.addr, mask.maskValue(action.value))
        })
    }

    private fun State.reduceV2(action: Action): State = when (action) {
        is Action.SetMask -> copy(mask = Mask(action.mask))
        is Action.SetValue -> copy(memory = memory.toMutableMap().apply {
            mask.maskAddress(action.addr).forEach { addr ->
                put(addr, action.value)
            }
        })
    }

    fun step1(): Long {
        return actions
            .fold(State()) { acc, action -> acc.reduceV1(action) }
            .memory.values
            .sum()
    }

    fun step2(): Long {
        return actions
            .fold(State()) { acc, action -> acc.reduceV2(action) }
            .memory.values
            .sum()
    }
}