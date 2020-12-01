val program = intArrayOf(1, 12, 2, 3, 1, 1, 2, 3, 1, 3, 4, 3, 1, 5, 0, 3, 2, 6, 1, 19, 2, 19, 13, 23, 1, 23, 10, 27, 1, 13, 27, 31, 2, 31, 10, 35, 1, 35, 9, 39, 1, 39, 13, 43, 1, 13, 43, 47, 1, 47, 13, 51, 1, 13, 51, 55, 1, 5, 55, 59, 2, 10, 59, 63, 1, 9, 63, 67, 1, 6, 67, 71, 2, 71, 13, 75, 2, 75, 13, 79, 1, 79, 9, 83, 2, 83, 10, 87, 1, 9, 87, 91, 1, 6, 91, 95, 1, 95, 10, 99, 1, 99, 13, 103, 1, 13, 103, 107, 2, 13, 107, 111, 1, 111, 9, 115, 2, 115, 10, 119, 1, 119, 5, 123, 1, 123, 2, 127, 1, 127, 5, 0, 99, 2, 14, 0, 0)

private const val OP_ADD = 1
private const val OP_MULT = 2
private const val OP_STOP = 99

private const val INSTR_SIZE = 4

private fun paramOpCode(input: IntArray, i: Int, op: (Int, Int) -> Int): IntArray {
    val ia = input[i + 1]
    val ib = input[i + 2]
    val ir = input[i + 3]

    val output = input.copyOf()
    output[ir] = op(input[ia], input[ib])
    return output
}

tailrec fun run(input: IntArray, i: Int = 0): IntArray {
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

fun runWithParams(noun: Int, verb: Int): Int {
    val input = program.copyOf()
    input[1] = noun
    input[2] = verb
    val output = run(input)
    return output[0]
}

fun step1() {
    println("output = ${run(program).joinToString()}")
}

fun step2() {
    val wanted = 19690720

    for (noun in 0..99) {
        for (verb in 0..99) {
            if (runWithParams(noun, verb) == wanted) {
                println("found it! noun=$noun, verb=$verb")
                println("answer = ${1000 * noun + verb}")
                return
            }
        }
    }
}

println(step1())
println(step2())
