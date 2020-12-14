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

        fun maskValue(value: Long): Long =
            mask.reversed()
                .foldIndexed(value) { n, acc, bit ->
                    // Current 'bit' in mask is 'X', '0' or '1'
                    when (bit) {
                        // Set nth bit to 1
                        '1' -> acc or (1L shl n)
                        // Set nth bit to 0
                        '0' -> acc and (1L shl n).inv()
                        // Don't change anything
                        else -> acc
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

    private fun State.reduce(action: Action): State = when (action) {
        is Action.SetMask -> copy(mask = Mask(action.mask))
        is Action.SetValue -> copy(memory = memory.toMutableMap().apply {
            set(action.addr, mask.maskValue(action.value))
        })
    }

    override fun step1(): Long {
        val finalState = actions.fold(State()) { acc, action ->
            acc.reduce(action)
        }
        return finalState.memory.values.sum()
    }

    override fun step2(): Long {
        TODO("Not yet implemented")
    }
}