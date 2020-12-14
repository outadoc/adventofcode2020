package fr.outadoc.aoc.twentytwenty

import fr.outadoc.aoc.scaffold.Day
import fr.outadoc.aoc.scaffold.Year

class Day14 : Day(Year.TwentyTwenty) {

    companion object {
        private const val ADDR_SIZE_BITS = 36
        private const val MEMORY_SIZE = 1 shl ADDR_SIZE_BITS

        private val maskRegex = Regex("^mask = ([01X]+)$")
        private val setRegex = Regex("^mem\\[([0-9]+)] = ([0-9]+)$")
    }

    private data class Mask(val mask: CharArray) {
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (other == null || this::class != other::class) return false

            other as Mask

            if (!mask.contentEquals(other.mask)) return false

            return true
        }

        override fun hashCode(): Int {
            return mask.contentHashCode()
        }
    }

    private data class State(
        val mask: Mask = Mask("X".repeat(ADDR_SIZE_BITS).toCharArray()),
        val memory: LongArray = LongArray(MEMORY_SIZE)
    ) {
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (other == null || this::class != other::class) return false

            other as State

            if (mask != other.mask) return false
            if (!memory.contentEquals(other.memory)) return false

            return true
        }

        override fun hashCode(): Int {
            var result = mask.hashCode()
            result = 31 * result + memory.contentHashCode()
            return result
        }
    }

    private sealed class Action {
        data class SetMask(val mask: String) : Action()
        data class SetValue(val addr: Long, val value: Long) : Action()
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

    override fun step1(): Long {
        actions.forEach { println(it) }
        return -1
    }

    override fun step2(): Long {
        TODO("Not yet implemented")
    }
}