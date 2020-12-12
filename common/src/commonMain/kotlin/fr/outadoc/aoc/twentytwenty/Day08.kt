package fr.outadoc.aoc.twentytwenty

import fr.outadoc.aoc.scaffold.Day
import fr.outadoc.aoc.scaffold.Year

class Day08 : Day(Year.TwentyTwenty) {

    data class Instruction(val op: Operation, val arg: Int)

    enum class Operation {
        ACC, JMP, NOP
    }

    private fun String.toOperation() = when (this) {
        "acc" -> Operation.ACC
        "jmp" -> Operation.JMP
        "nop" -> Operation.NOP
        else -> throw IllegalArgumentException()
    }

    private val program: List<Instruction> =
        readDayInput()
            .lines()
            .map { line ->
                Instruction(
                    op = line.takeWhile { it != ' ' }.toOperation(),
                    arg = line.takeLastWhile { it != ' ' }.toInt()
                )
            }

    class InfiniteLoopException(val acc: Long) : Exception()

    class CPU(private val program: List<Instruction>) {

        private var pc: Int = 0
        private var acc: Long = 0

        private val instructionStepCount = IntArray(program.size)

        fun execute(): Long {
            @Suppress("ControlFlowWithEmptyBody")
            while (executeInstruction(program[pc])) {
            }
            return acc
        }

        private fun executeInstruction(ins: Instruction): Boolean {
            // Infinite loop detector
            if (instructionStepCount[pc] > 0) {
                throw InfiniteLoopException(acc)
            }

            instructionStepCount[pc]++

            when (ins.op) {
                Operation.ACC -> {
                    acc += ins.arg
                    pc++
                }
                Operation.JMP -> {
                    pc += ins.arg
                }
                Operation.NOP -> {
                    pc++
                }
            }

            // Normal stop condition
            if (pc >= program.size) {
                return false
            }

            return true
        }
    }

    override fun step1(): Long {
        try {
            CPU(program).execute()
        } catch (e: InfiniteLoopException) {
            return e.acc
        }

        throw IllegalStateException("this should always throw an exception")
    }

    override fun step2(): Long {
        for (insToPatch in program) {
            val patch = program.map { currentIns ->
                // Check if this is the instruction we want to patch (by reference)
                if (currentIns === insToPatch) {
                    when (currentIns.op) {
                        Operation.JMP -> currentIns.copy(op = Operation.NOP)
                        Operation.NOP -> currentIns.copy(op = Operation.JMP)
                        else -> currentIns
                    }

                } else currentIns
            }

            try {
                return CPU(patch).execute()
            } catch (e: InfiniteLoopException) {
                // This wasn't the right patch, retry
            }
        }

        throw IllegalStateException("the program should return before this")
    }
}