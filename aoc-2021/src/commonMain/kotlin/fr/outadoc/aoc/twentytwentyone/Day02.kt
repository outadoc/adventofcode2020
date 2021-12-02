package fr.outadoc.aoc.twentytwentyone

import fr.outadoc.aoc.scaffold.Day
import fr.outadoc.aoc.scaffold.readDayInput

class Day02 : Day<Int> {

    private data class State(
        val x: Int = 0,
        val depth: Int = 0,
        val aim: Int = 0
    )

    private sealed class Action {
        data class Up(val count: Int) : Action()
        data class Down(val count: Int) : Action()
        data class Forward(val count: Int) : Action()
    }

    private fun String.parse(): Action {
        val (action, param) = split(' ')
        return when (action) {
            "up" -> Action.Up(count = param.toInt())
            "down" -> Action.Down(count = param.toInt())
            "forward" -> Action.Forward(count = param.toInt())
            else -> throw IllegalArgumentException("unknown action $action")
        }
    }

    private val actions = readDayInput()
        .lineSequence()
        .map { action -> action.parse() }

    override fun step1(): Int {
        fun State.reduce(action: Action) = when (action) {
            is Action.Up -> copy(depth = depth - action.count)
            is Action.Down -> copy(depth = depth + action.count)
            is Action.Forward -> copy(x = x + action.count)
        }

        return actions
            .fold(State()) { acc, action -> acc.reduce(action) }
            .run { x * depth }
    }

    override fun step2(): Int {
        fun State.reduce(action: Action) = when (action) {
            is Action.Up -> copy(aim = aim - action.count)
            is Action.Down -> copy(aim = aim + action.count)
            is Action.Forward -> copy(
                x = x + action.count,
                depth = depth + aim * action.count
            )
        }

        return actions
            .fold(State()) { acc, action -> acc.reduce(action) }
            .run { x * depth }
    }

    override val expectedStep1 = 1990000
    override val expectedStep2 = 1975421260
}