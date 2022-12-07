package fr.outadoc.aoc.twentytwentytwo

import fr.outadoc.aoc.scaffold.Day
import fr.outadoc.aoc.scaffold.head
import fr.outadoc.aoc.scaffold.readDayInput

class Day07 : Day<Int> {

    private sealed class Node {

        abstract val name: String
        abstract val size: Int

        data class Directory(
            override val name: String,
            val children: MutableList<Node> = mutableListOf()
        ) : Node() {
            override val size: Int
                get() = children.sumOf { child -> child.size }

            override fun toString(): String = "$name (dir)"
        }

        data class File(
            override val name: String,
            override val size: Int
        ) : Node() {
            override fun toString(): String = "$name (file, size=$size)"
        }
    }

    private sealed class Command {
        data class ChangeDir(val path: String) : Command()
        data class ListDir(val contents: List<String>) : Command()
    }

    private val commands: List<Command> =
        readDayInput()
            .split("$ ")
            .map { commandBlock ->
                commandBlock.replace("$ ", "")
                    .lines()
                    .filterNot { line -> line == "" }
            }
            .filter { it.isNotEmpty() }
            .map { command ->
                val (cmdLine, results) = command.head()
                when (val cmd = cmdLine.take(2)) {
                    "ls" -> Command.ListDir(results)
                    "cd" -> Command.ChangeDir(path = cmdLine.drop(3))
                    else -> error("unknown command: $cmd")
                }
            }

    private fun buildFs(commands: List<Command>): Node.Directory {
        val root = Node.Directory("/")
        val currentPath = ArrayDeque<Node.Directory>()

        commands.forEach { command ->
            when (command) {
                is Command.ChangeDir -> {
                    when (command.path) {
                        "/" -> {
                            currentPath.clear()
                            currentPath.addLast(root)
                        }

                        ".." -> {
                            currentPath.removeLast()
                        }

                        else -> {
                            currentPath.addLast(
                                currentPath.last()
                                    .children
                                    .filterIsInstance<Node.Directory>()
                                    .first { child ->
                                        child.name == command.path
                                    }
                            )
                        }
                    }
                }

                is Command.ListDir -> {
                    command.contents.map { node ->
                        val (a, fileName) = node.split(' ')
                        when (a) {
                            "dir" -> {
                                currentPath.last().children.add(
                                    Node.Directory(fileName)
                                )
                            }

                            else -> {
                                currentPath.last().children.add(
                                    Node.File(
                                        name = fileName,
                                        size = a.toInt()
                                    )
                                )
                            }
                        }
                    }
                }
            }
        }

        return root
    }

    private fun Node.print(depth: Int = 0) {
        repeat(depth * 2) {
            print(" ")
        }

        println("- ${toString()}")

        if (this is Node.Directory) {
            children.forEach { child ->
                child.print(depth = depth + 1)
            }
        }
    }

    private fun Node.Directory.listAllSubDirs(): List<Node.Directory> {
        return listOf(this) + children.filterIsInstance<Node.Directory>().flatMap { it.listAllSubDirs() }
    }

    override fun step1(): Int {
        val root = buildFs(commands)
        val maxSize = 100_000
        return root.listAllSubDirs()
            .filter { it.size < maxSize }
            .sumOf { it.size }
    }

    override val expectedStep1 = 1_084_134

    override fun step2(): Int {
        val root = buildFs(commands)

        val totalSpace = 70_000_000
        val requiredSpace = 30_000_000

        val usedSpace = root.size
        val unusedSpace = totalSpace - usedSpace
        val maxSize = requiredSpace - unusedSpace

        return root.listAllSubDirs()
            .filter { it.size >= maxSize }
            .minOf { it.size }
    }

    override val expectedStep2 = 6_183_184
}
