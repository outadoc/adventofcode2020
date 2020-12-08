package fr.outadoc.aoc.y2020

import fr.outadoc.aoc.scaffold.Day
import fr.outadoc.aoc.scaffold.Year

class Day8 : Day(Year._2020) {

    data class Instruction(val ins: String, val arg: Int)

    private val program = readDayInput()
        .lines()
        .filterNot { it.isEmpty() }
        .map { line ->
            Instruction(
                ins = line.takeWhile { it != ' ' },
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

            when (ins.ins) {
                "acc" -> {
                    acc += ins.arg
                    pc++
                }
                "jmp" -> {
                    pc += ins.arg
                }
                "nop" -> {
                    pc++
                }
                else -> throw IllegalArgumentException()
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
        for (ins in program) {
            val patch = program.map {
                if (it == ins) {
                    // This is the instruction we want to patch
                    when (ins.ins) {
                        "jmp" -> ins.copy(ins = "nop")
                        "nop" -> ins.copy(ins = "jmp")
                        else -> ins
                    }
                } else it
            }

            try {
                return CPU(patch).execute()
            } catch (e: InfiniteLoopException) {
            }
        }

        throw IllegalStateException("the program should return before this")
    }
}