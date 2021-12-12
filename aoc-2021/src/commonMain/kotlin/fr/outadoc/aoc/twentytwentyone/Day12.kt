package fr.outadoc.aoc.twentytwentyone

import fr.outadoc.aoc.scaffold.Day
import fr.outadoc.aoc.scaffold.readDayInput

class Day12 : Day<Int> {

    private enum class NodeType {
        Start,
        End,
        SmallCave,
        BigCave
    }

    private data class Node(
        val id: String,
        val connectedTo: MutableSet<Node> = mutableSetOf()
    ) {
        val type: NodeType = when (id) {
            "start" -> NodeType.Start
            "end" -> NodeType.End
            id.lowercase() -> NodeType.SmallCave
            else -> NodeType.BigCave
        }

        override fun toString() = "$id -> " + connectedTo.joinToString { node -> node.id }

        override fun hashCode() = id.hashCode()

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (other == null || this::class != other::class) return false

            other as Node

            if (id != other.id) return false

            return true
        }
    }

    private val nodes =
        readDayInput()
            .lineSequence()
            .map { line -> line.split('-') }
            .fold(emptySet<Node>()) { acc, (startId, endId) ->
                val startNode = acc.firstOrNull { node -> node.id == startId } ?: Node(id = startId)
                val endNode = acc.firstOrNull { node -> node.id == endId } ?: Node(id = endId)

                startNode.connectedTo += endNode
                endNode.connectedTo += startNode

                acc + startNode + endNode
            }

    private val startNode = nodes.first { node -> node.type == NodeType.Start }
    private val endNode = nodes.first { node -> node.type == NodeType.End }

    private tailrec fun findAllPaths(
        visitedNodes: Set<List<Node>>,
        allowDoubleSmallCaveVisit: Boolean
    ): Set<List<Node>> {
        if (visitedNodes.all { node -> node.last() == endNode })
            return visitedNodes

        val nodes: Set<List<Node>> = visitedNodes
            .flatMap { chain: List<Node> ->
                val wasSmallCaveVisitedTwiceAlready =
                    chain.asSequence()
                        .filter { node -> node.type == NodeType.SmallCave }
                        .groupingBy { node -> node.id }
                        .eachCount()
                        .any { (_, count) -> count > 1 }

                when (val current: Node = chain.last()) {
                    endNode -> listOf(chain)
                    else -> current
                        .connectedTo
                        .filterNot { node -> node == startNode }
                        .filter { node ->
                            when (node.type) {
                                NodeType.SmallCave ->
                                    when (chain.count { it == node }) {
                                        0 -> true
                                        1 -> allowDoubleSmallCaveVisit && !wasSmallCaveVisitedTwiceAlready
                                        else -> false
                                    }
                                else -> true
                            }
                        }
                        .map { node -> chain + node }
                }
            }
            .toSet()

        return findAllPaths(
            visitedNodes = nodes,
            allowDoubleSmallCaveVisit = allowDoubleSmallCaveVisit
        )
    }

    override fun step1() =
        findAllPaths(
            visitedNodes = setOf(listOf(startNode)),
            allowDoubleSmallCaveVisit = false
        ).size

    override fun step2() =
        findAllPaths(
            visitedNodes = setOf(listOf(startNode)),
            allowDoubleSmallCaveVisit = true
        ).size

    override val expectedStep1 = 3292
    override val expectedStep2 = 89592
}
