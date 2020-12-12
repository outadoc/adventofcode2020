package fr.outadoc.aoc.twentytwenty

import fr.outadoc.aoc.scaffold.Day
import fr.outadoc.aoc.scaffold.Year
import kotlin.math.abs

class Day12 : Day(Year.TwentyTwenty) {

    sealed class Action(open val value: Int) {
        data class North(override val value: Int) : Action(value)
        data class South(override val value: Int) : Action(value)
        data class East(override val value: Int) : Action(value)
        data class West(override val value: Int) : Action(value)
        data class TurnLeft(override val value: Int) : Action(value)
        data class TurnRight(override val value: Int) : Action(value)
        data class MoveForward(override val value: Int) : Action(value)
    }

    enum class Direction {
        NORTH, SOUTH, EAST, WEST
    }

    data class State(val x: Int, val y: Int, val currentDirection: Direction)

    private val actions: Sequence<Action> =
        readDayInput()
            .lineSequence()
            .map { action ->
                val value = action.drop(1).toInt()
                when (action.first()) {
                    'N' -> Action.North(value)
                    'S' -> Action.South(value)
                    'E' -> Action.East(value)
                    'W' -> Action.West(value)
                    'L' -> Action.TurnLeft(value)
                    'R' -> Action.TurnRight(value)
                    'F' -> Action.MoveForward(value)
                    else -> throw IllegalArgumentException()
                }
            }

    private val initialState = State(x = 0, y = 0, currentDirection = Direction.EAST)

    private fun State.reduce(action: Action): State {
        return when (action) {
            is Action.North -> moveNorth(action.value)
            is Action.South -> moveSouth(action.value)
            is Action.East -> moveEast(action.value)
            is Action.West -> moveWest(action.value)
            is Action.TurnLeft -> turnAngle(-action.value)
            is Action.TurnRight -> turnAngle(action.value)
            is Action.MoveForward -> when (currentDirection) {
                Direction.NORTH -> moveNorth(action.value)
                Direction.SOUTH -> moveSouth(action.value)
                Direction.EAST -> moveEast(action.value)
                Direction.WEST -> moveWest(action.value)
            }
        }
    }

    private fun State.moveNorth(value: Int) = copy(y = y + value)
    private fun State.moveSouth(value: Int) = copy(y = y - value)
    private fun State.moveEast(value: Int) = copy(x = x + value)
    private fun State.moveWest(value: Int) = copy(x = x - value)

    private tailrec fun State.turnAngle(angle: Int): State {
        val newDir = when {
            angle > 0 -> when (currentDirection) {
                Direction.NORTH -> Direction.EAST
                Direction.SOUTH -> Direction.WEST
                Direction.EAST -> Direction.SOUTH
                Direction.WEST -> Direction.NORTH
            }
            angle < 0 -> when (currentDirection) {
                Direction.NORTH -> Direction.WEST
                Direction.SOUTH -> Direction.EAST
                Direction.EAST -> Direction.NORTH
                Direction.WEST -> Direction.SOUTH
            }
            else -> return this
        }

        val nextAngle = when {
            angle > 0 -> angle - 90
            else -> angle + 90
        }

        return copy(currentDirection = newDir).turnAngle(nextAngle)
    }

    private val State.manhattanDistance: Long
        get() = abs(x.toLong()) + abs(y.toLong())

    override fun step1(): Long {
        println(initialState)
        val finalState = actions.fold(initialState) { acc, action ->
            acc.reduce(action).also {
                println(it)
            }
        }

        return finalState.manhattanDistance
    }

    override fun step2(): Long {
        TODO("Not yet implemented")
    }
}