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

            return true
        }

        override fun hashCode(): Int {
            return position.hashCode()
        }

        override fun toString(): String =
            "Node(position=$position, risk=$risk, neighbors=[${neighbors.joinToString { it.position.toString() }}])"
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

    private fun Set<Node>.findStart(): Node =
        first { it.position == Position(x = 0, y = 0) }

    private fun Set<Node>.findDestination(): Node {
        val xMax = maxOf { it.position.x }
        val yMax = maxOf { it.position.y }
        return first { it.position == Position(x = xMax, y = yMax) }
    }

    private fun List<List<Int>>.toNodes(): Set<Node> {
        val nodes = flatMapIndexed { y, line ->
            line.mapIndexed { x, risk ->
                Node(
                    position = Position(x = x, y = y),
                    risk = risk,
                    neighbors = mutableSetOf()
                )
            }
        }.toSet()

        return nodes.onEach { node ->
            node.neighbors.addAll(
                node.position
                    .surrounding
                    .mapNotNull { neighborPos ->
                        nodes.getNodeByPos(neighborPos)
                    }
            )
        }
    }

    private fun Set<Node>.getNodeByPos(position: Position): Node? =
        firstOrNull { node -> node.position == position }

    private tailrec fun dijkstra(
        currentNode: Node,
        destination: Node,
        unvisitedNodes: Set<Node>,
        tentativeRisks: Map<Node, Pair<Node?, Int>> = mapOf(currentNode to (null to 0))
    ): List<Node> {
        val nextUnvisitedNodes = unvisitedNodes - currentNode
        val currentNodeRisk = tentativeRisks.getOrElse(currentNode) { null to Int.MAX_VALUE }.second

        val currentNeighborRisks = currentNode.neighbors
            .filter { neighbor -> neighbor in nextUnvisitedNodes }
            .associateWith { neighbor ->
                val existingRisk = tentativeRisks.getOrElse(neighbor) { null to Int.MAX_VALUE }
                val newRisk = currentNodeRisk + neighbor.risk
                if (newRisk < existingRisk.second) currentNode to newRisk
                else existingRisk
            }

        val nextRisks = tentativeRisks + currentNeighborRisks
        val nextNode = nextRisks
            .filterKeys { node -> node in nextUnvisitedNodes }
            .minByOrNull { (_, risk) -> risk.second }!!.key

        if (nextNode == destination) {
            return findDestination(listOf(destination), nextRisks)
        }

        return dijkstra(
            currentNode = nextNode,
            destination = destination,
            unvisitedNodes = nextUnvisitedNodes,
            tentativeRisks = nextRisks
        )
    }

    private tailrec fun findDestination(
        acc: List<Node>,
        tentativeRisks: Map<Node, Pair<Node?, Int>>
    ): List<Node> {
        val current = acc.first()
        val parent = tentativeRisks.getValue(current).first ?: return acc
        return findDestination(listOf(parent) + acc, tentativeRisks)
    }

    private fun List<List<Int>>.expand(times: Int): List<List<Int>> {
        return (0 until times).flatMap { i ->
            map { line ->
                (0 until times).flatMap { j ->
                    line.map { c ->
                        val inc = c + i + j
                        if (inc > 9) inc % 10 + 1
                        else inc
                    }
                }
            }
        }
    }

    override fun step1(): Int {
        val nodes = riskMap.toNodes()

        val shortestPath = dijkstra(
            currentNode = nodes.findStart(),
            destination = nodes.findDestination(),
            unvisitedNodes = nodes
        )

        return shortestPath
            .drop(1)
            .sumOf { node -> node.risk }
    }

    override fun step2(): Int {
        val nodes = riskMap.expand(times = 5).toNodes()

        val shortestPath = dijkstra(
            currentNode = nodes.findStart(),
            destination = nodes.findDestination(),
            unvisitedNodes = nodes
        )

        return shortestPath
            .drop(1)
            .sumOf { node -> node.risk }
    }

    override val expectedStep1 = 656
}
