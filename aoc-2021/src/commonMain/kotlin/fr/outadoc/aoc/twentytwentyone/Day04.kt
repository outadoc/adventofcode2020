package fr.outadoc.aoc.twentytwentyone

import fr.outadoc.aoc.scaffold.Day
import fr.outadoc.aoc.scaffold.readDayInput

class Day04 : Day<Int> {

    private val input = readDayInput()
        .split("\n\n")
        .map { chunk -> chunk.lines() }

    private val draw = input
        .first()
        .first()
        .split(',')
        .map { num -> num.toInt() }

    private val boards = input
        .drop(1)
        .map { board ->
            Board(
                board.map { line ->
                    line.split(' ')
                        .filter { it.isNotBlank() }
                        .map { num -> num.toInt() }
                }
            )
        }

    private class Board(private val lines: List<List<Int>>) {
        private val size: Int = lines.size

        operator fun get(x: Int, y: Int): Int {
            check(x in 0 until size)
            check(y in 0 until size)
            return lines[y][x]
        }

        fun hasWon(drawnNumbers: Set<Int>): Boolean =
            (0 until size).any { i ->
                isHorizontalWin(drawnNumbers, i) || isVerticalWin(drawnNumbers, i)
            }

        private fun isHorizontalWin(drawnNumbers: Set<Int>, y: Int): Boolean {
            return lines[y].all { num -> num in drawnNumbers }
        }

        private fun isVerticalWin(drawnNumbers: Set<Int>, x: Int): Boolean {
            return (0 until size).all { y ->
                this[x, y] in drawnNumbers
            }
        }

        fun getScore(drawnNumbers: Set<Int>): Int {
            val sum = lines.sumOf { line ->
                line.sumOf { num ->
                    if (num in drawnNumbers) 0 else num
                }
            }

            return sum * drawnNumbers.last()
        }

        fun println(drawnNumbers: Set<Int>) {
            lines.forEach { line ->
                println(
                    line.joinToString("") { num ->
                        val marked = num in drawnNumbers
                        ((if (marked) "[" else " ") + num + (if (marked) "]" else " ")).padStart(4)
                    }
                )
            }
        }
    }

    private data class BoardState(val boards: List<Board>, val drawnNumbers: Set<Int>) {

        val winningBoards: Set<Board> = boards.filter { board -> board.hasWon(drawnNumbers) }.toSet()

        fun printState() {
            boards.forEachIndexed { index, board ->
                println()
                println("board $index; hasWon=${board.hasWon(drawnNumbers)}")
                board.println(drawnNumbers)
            }
        }
    }

    override fun step1(): Int {
        draw.fold(BoardState(boards, emptySet())) { state, drawnNumber ->
            state.winningBoards.firstOrNull()?.let { winner ->
                return winner.getScore(state.drawnNumbers)
            }

            state.copy(drawnNumbers = state.drawnNumbers + drawnNumber)
        }

        throw IllegalStateException("No winners")
    }

    override fun step2(): Int {
        draw.fold(BoardState(boards, emptySet())) { state, drawnNumber ->
            val nextState = state.copy(drawnNumbers = state.drawnNumbers + drawnNumber)

            // If everybody won
            if (nextState.winningBoards.size == nextState.boards.size) {
                // Get the latest winner and return their score
                val newWinners = nextState.winningBoards - state.winningBoards
                return newWinners.first().getScore(nextState.drawnNumbers)
            }

            nextState
        }

        throw IllegalStateException("Not everybody won")
    }

    override val expectedStep1: Int = 2745
    override val expectedStep2: Int = 6594
}
