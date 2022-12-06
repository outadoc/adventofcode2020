package fr.outadoc.aoc.twentytwentytwo

import fr.outadoc.aoc.scaffold.Day
import fr.outadoc.aoc.scaffold.readDayInput

class Day05 : Day<String> {

    private val initialState: State =
        readDayInput()
            .splitToSequence("\n\n")
            .first()
            .lines()
            .parseState()

    private val instructions: Sequence<Instruction> =
        readDayInput()
            .splitToSequence("\n\n")
            .last()
            .lineSequence()
            .map { line -> line.parseInstruction() }

    private data class State(val stacks: List<List<Char>>)
    private data class Instruction(val count: Int, val from: Int, val to: Int)

    private fun List<String>.parseState(): State {
        val lines = dropLast(1).map { line ->
            line.chunked(size = 4) {
                it.replace(Regex("\\W"), "")
                    .trim()
                    .toCharArray()
                    .toList()
                    .ifEmpty { listOf(' ') }
                    .first()
            }
        }

        val stackCount = lines.first().size
        val stacks: List<MutableList<Char>> = List(stackCount) { mutableListOf() }

        lines.forEach { chars ->
            chars.forEachIndexed { index, c ->
                if (c != ' ') {
                    stacks[index].add(c)
                }
            }
        }

        return State(stacks = stacks.map { stack -> stack.reversed() })
    }

    private val instructionRegex = Regex("^move ([0-9]+) from ([0-9]+) to ([0-9]+)$")

    private fun String.parseInstruction(): Instruction {
        val values = instructionRegex.find(this)!!.groupValues
        return Instruction(
            count = values[1].toInt(),
            from = values[2].toInt(),
            to = values[3].toInt()
        )
    }

    private fun State.reduce(instruction: Instruction): State {
        val tail = stacks[instruction.from - 1].takeLast(instruction.count)
        return copy(
            stacks = stacks.mapIndexed { index, chars ->
                when (index + 1) {
                    instruction.from -> chars.dropLast(instruction.count)
                    instruction.to -> chars + tail
                    else -> chars
                }
            }
        )
    }

    private fun State.print() {
        stacks.forEachIndexed { index, chars ->
            println("$index ${chars.joinToString(separator = " ") { "[$it]" }}")
        }
        println()
    }

    override fun step1(): String {
        val unfoldedInstructions =
            instructions.flatMap { instruction ->
                List(instruction.count) {
                    instruction.copy(count = 1)
                }
            }

        val finalState: State =
            unfoldedInstructions.fold(initialState) { acc, instruction ->
                acc.reduce(instruction)
            }

        return finalState.stacks
            .map { stack -> stack.last() }
            .joinToString(separator = "")
    }

    override val expectedStep1 = "HNSNMTLHQ"

    override fun step2(): String {
        val finalState: State =
            instructions.fold(initialState) { acc, instruction ->
                acc.print()
                println(instruction)
                acc.reduce(instruction)
            }

        finalState.print()

        return finalState.stacks
            .map { stack -> stack.last() }
            .joinToString(separator = "")
    }

    override val expectedStep2 = "RNLFDJMCT"
}
