package fr.outadoc.aoc.twentynineteen

import fr.outadoc.aoc.scaffold.Day
import fr.outadoc.aoc.scaffold.Year

class Day02 : Day(Year.TwentyNineteen) {

    private val program: IntArray =
        readDayInput()
            .lines()
            .map { it.toInt() }
            .toIntArray()

    companion object {
        const val OP_ADD = 1
        const val OP_MULT = 2
        const val OP_STOP = 99

        const val INSTR_SIZE = 4
    }

    private fun paramOpCode(input: IntArray, i: Int, op: (Int, Int) -> Int): IntArray {
        val ia = input[i + 1]
        val ib = input[i + 2]
        val ir = input[i + 3]

        val output = input.copyOf()
        output[ir] = op(input[ia], input[ib])
        return output
    }

    private tailrec fun run(input: IntArray, i: Int = 0): IntArray {
        //println("running i=$i on ${input.joinToString()}")
        return when (val opCode = input[i]) {
            OP_ADD -> run(paramOpCode(input, i) { a, b -> a + b }, i + INSTR_SIZE)
            OP_MULT -> run(paramOpCode(input, i) { a, b -> a * b }, i + INSTR_SIZE)
            OP_STOP -> {
                //println("program end")
                input
            }
            else -> throw IllegalStateException("opcode $opCode is invalid")
        }
    }

    private fun runWithParams(noun: Int, verb: Int): Int {
        val input = program.copyOf()
        input[1] = noun
        input[2] = verb
        val output = run(input)
        return output[0]
    }

    fun step1(): Int {
        return run(program).first()
    }

    fun step2(): Int {
        val wanted = 19690720

        for (noun in 0..99) {
            for (verb in 0..99) {
                if (runWithParams(noun, verb) == wanted) {
                    println("found it! noun=$noun, verb=$verb")
                    return (1000 * noun + verb)
                }
            }
        }

        return -1
    }
}