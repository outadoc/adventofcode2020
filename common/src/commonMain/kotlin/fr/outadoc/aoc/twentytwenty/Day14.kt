package fr.outadoc.aoc.twentytwenty

import fr.outadoc.aoc.scaffold.Day
import fr.outadoc.aoc.scaffold.Year

class Day14 : Day(Year.TwentyTwenty) {

    companion object {
        private const val WORD_SIZE: Int = 36

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
                // Current 'bit' in mask is 'X', '0' or '1'
                when (bit) {
                    '0' -> acc.clearBit(n)
                    '1' -> acc.setBit(n)
                    else -> acc
                }
            }

        fun maskAddress(addr: Long, n: Int = 0): List<Long> {
            if (n !in mask.indices) return listOf(addr)
            return when (reverseMask[n]) {
                '0' -> maskAddress(addr, n + 1)
                '1' -> maskAddress(addr.setBit(n), n + 1)
                else -> maskAddress(addr.setBit(n), n + 1) + maskAddress(addr.clearBit(n), n + 1)
            }
        }
    }

    private data class State(
        val mask: Mask = Mask("X".repeat(WORD_SIZE)),
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

    override fun step1(): Long {
        return actions
            .fold(State()) { acc, action -> acc.reduceV1(action) }
            .memory.values
            .sum()
    }

    override fun step2(): Long {
        return actions
            .fold(State()) { acc, action -> acc.reduceV2(action) }
            .memory.values
            .sum()
    }
}