package fr.outadoc.aoc.twentytwenty

import fr.outadoc.aoc.scaffold.Day
import fr.outadoc.aoc.scaffold.Year
import kotlin.math.*

class Day12 : Day(Year.TwentyTwenty) {

    private sealed class Action {
        data class AddNorth(val units: Int) : Action()
        data class AddSouth(val units: Int) : Action()
        data class AddEast(val units: Int) : Action()
        data class AddWest(val units: Int) : Action()

        data class TurnLeft(val angle: Int) : Action()
        data class TurnRight(val angle: Int) : Action()

        data class MoveForward(val units: Int) : Action()
    }

    private val actions: Sequence<Action> =
        readDayInput()
            .lineSequence()
            .map { action ->
                val value = action.drop(1).toInt()
                when (action.first()) {
                    'N' -> Action.AddNorth(value)
                    'S' -> Action.AddSouth(value)
                    'E' -> Action.AddEast(value)
                    'W' -> Action.AddWest(value)
                    'L' -> Action.TurnLeft(value)
                    'R' -> Action.TurnRight(value)
                    'F' -> Action.MoveForward(value)
                    else -> throw IllegalArgumentException()
                }
            }

    private enum class Direction {
        NORTH, SOUTH, EAST, WEST
    }

    private tailrec fun Direction.rotate(angle: Int): Direction {
        val newDir = when {
            angle > 0 -> when (this) {
                Direction.NORTH -> Direction.EAST
                Direction.SOUTH -> Direction.WEST
                Direction.EAST -> Direction.SOUTH
                Direction.WEST -> Direction.NORTH
            }
            angle < 0 -> when (this) {
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

        return newDir.rotate(nextAngle)
    }

    private data class Position(val x: Int, val y: Int) {
        val manhattanDistance: Long
            get() = abs(x.toLong()) + abs(y.toLong())
    }

    private operator fun Position.plus(other: Position): Position =
        copy(x = x + other.x, y = y + other.y)

    private operator fun Position.times(times: Int): Position =
        copy(x = x * times, y = y * times)

    private fun Position.moveNorth(value: Int) = copy(y = y + value)
    private fun Position.moveSouth(value: Int) = copy(y = y - value)
    private fun Position.moveEast(value: Int) = copy(x = x + value)
    private fun Position.moveWest(value: Int) = copy(x = x - value)

    private fun Position.rotateRelativeToOrigin(angle: Int): Position {
        val sin = sin(-angle * PI / 180)
        val cos = cos(-angle * PI / 180)

        return Position(
            x = (x * cos - y * sin).roundToInt(),
            y = (x * sin + y * cos).roundToInt()
        )
    }

    private data class State1(
        val shipPosition: Position,
        val currentDirection: Direction
    )

    private fun State1.reduce(action: Action) = when (action) {
        is Action.AddNorth -> copy(shipPosition = shipPosition.moveNorth(action.units))
        is Action.AddSouth -> copy(shipPosition = shipPosition.moveSouth(action.units))
        is Action.AddEast -> copy(shipPosition = shipPosition.moveEast(action.units))
        is Action.AddWest -> copy(shipPosition = shipPosition.moveWest(action.units))

        is Action.TurnLeft -> copy(currentDirection = currentDirection.rotate(-action.angle))
        is Action.TurnRight -> copy(currentDirection = currentDirection.rotate(action.angle))

        is Action.MoveForward -> when (currentDirection) {
            Direction.NORTH -> copy(shipPosition = shipPosition.moveNorth(action.units))
            Direction.SOUTH -> copy(shipPosition = shipPosition.moveSouth(action.units))
            Direction.EAST -> copy(shipPosition = shipPosition.moveEast(action.units))
            Direction.WEST -> copy(shipPosition = shipPosition.moveWest(action.units))
        }
    }

    private data class State2(
        val shipPosition: Position,
        val waypointRelPos: Position
    )

    private fun State2.reduce(action: Action) = when (action) {
        is Action.AddNorth -> copy(waypointRelPos = waypointRelPos.moveNorth(action.units))
        is Action.AddSouth -> copy(waypointRelPos = waypointRelPos.moveSouth(action.units))
        is Action.AddEast -> copy(waypointRelPos = waypointRelPos.moveEast(action.units))
        is Action.AddWest -> copy(waypointRelPos = waypointRelPos.moveWest(action.units))

        is Action.TurnLeft -> copy(waypointRelPos = waypointRelPos.rotateRelativeToOrigin(-action.angle))
        is Action.TurnRight -> copy(waypointRelPos = waypointRelPos.rotateRelativeToOrigin(action.angle))

        is Action.MoveForward -> copy(shipPosition = shipPosition + (waypointRelPos * action.units))
    }

    override fun step1(): Long {
        val initialState = State1(
            shipPosition = Position(x = 0, y = 0),
            currentDirection = Direction.EAST
        )

        val finalState = actions.fold(initialState) { acc, action ->
            acc.reduce(action)
        }

        return finalState.shipPosition.manhattanDistance
    }

    override fun step2(): Long {
        val initialState = State2(
            shipPosition = Position(x = 0, y = 0),
            waypointRelPos = Position(x = 10, y = 1)
        )

        val finalState = actions.fold(initialState) { acc, action ->
            acc.reduce(action)
        }

        return finalState.shipPosition.manhattanDistance
    }
}