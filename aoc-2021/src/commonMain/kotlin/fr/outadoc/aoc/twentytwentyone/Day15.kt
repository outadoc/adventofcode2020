package fr.outadoc.aoc.twentytwentyone

import fr.outadoc.aoc.scaffold.Day
import fr.outadoc.aoc.scaffold.readDayInput

class Day15 : Day<Int> {

    private data class Node(
        val position: Position,
        val risk: Int,
        val neighbors: MutableSet<Node>
    ) {
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (other == null || this::class != other::class) return false

            other as Node

            if (position != other.position) return false
            if (risk != other.risk) return false

            return true
        }

        override fun hashCode(): Int {
            var result = position.hashCode()
            result = 31 * result + risk
            return result
        }

        override fun toString(): String =
            "Node(position=$position, risk=$risk, neighbors=[${neighbors.joinToString { position.toString() }}])"
    }

    private data class Position(val x: Int, val y: Int)

    private val Position.surrounding: Set<Position>
        get() = setOf(
            copy(y = y - 1),
            copy(y = y + 1),
            copy(x = x - 1),
            copy(x = x + 1)
        )

    private val riskMap: List<List<Int>> =
        readDayInput()
            .lines()
            .map { line ->
                line.map { risk ->
                    risk.digitToInt()
                }
            }

    private val height = riskMap.size
    private val width = riskMap.first().size

    private val Node.isStart: Boolean
        get() = position == Position(x = 0, y = 0)

    private val Node.isEnd: Boolean
        get() = position == Position(x = width - 1, y = height - 1)

    private fun buildNodes(): Set<Node> {
        val nodes = riskMap
            .flatMapIndexed { y, line ->
                line.mapIndexed { x, risk ->
                    Node(
                        position = Position(x = x, y = y),
                        risk = risk,
                        neighbors = mutableSetOf()
                    )
                }
            }
            .toSet()

        return nodes.onEach { node ->
            node.neighbors.addAll(
                node.position.surrounding.mapNotNull { neighborPos ->
                    nodes.firstOrNull { neighbor ->
                        neighbor.position == neighborPos
                    }
                }
            )
        }
    }

    private val nodes: Set<Node> = buildNodes()

    override fun step1(): Int {
        nodes.forEach { node ->
            println(node)
        }
        return -1
    }
}
