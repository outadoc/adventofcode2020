package fr.outadoc.aoc.twentytwenty

import fr.outadoc.aoc.scaffold.Day
import fr.outadoc.aoc.scaffold.Year

class Day18 : Day(Year.TwentyTwenty) {

    private sealed class Expression {
        data class Constant(val n: Long) : Expression()
        data class Addition(val a: Expression, val b: Expression) : Expression()
        data class Product(val a: Expression, val b: Expression) : Expression()
        data class Parentheses(val a: Expression) : Expression()
    }

    private val input: Sequence<String> =
        readDayInput()
            .lineSequence()
            .map { it.replace(" ", "") }

    private fun parse(rest: List<Char>, previousExpr: Expression? = null): Expression {
        if (rest.isEmpty()) return previousExpr!!

        val head = rest.first()
        val tail = rest.drop(1)

        return when (head) {
            in '0'..'9' -> parse(tail, Expression.Constant(head.toString().toLong()))
            '+' -> Expression.Addition(parse(tail), previousExpr!!)
            '*' -> Expression.Product(parse(tail), previousExpr!!)
            ')' -> {
                val (contentInside, contentOutside) = parseInsideParens(tail)
                Expression.Parentheses(parse(contentOutside, parse(contentInside)))
            }
            else -> TODO()
        }
    }

    private fun parseInsideParens(content: List<Char>): Pair<List<Char>, List<Char>> {
        var openParens = 0
        content.forEachIndexed { index, c ->
            when (c) {
                ')' -> openParens++
                '(' -> if (openParens == 0) {
                    return content.subList(0, index) to content.subList(index + 1, content.size)
                } else {
                    openParens--
                }
            }
        }

        throw IllegalArgumentException("no matching parens")
    }

    private fun String.parse() = parse(toCharArray().toList().reversed())

    private fun Expression.solve(): Long {
        return when (this) {
            is Expression.Constant -> n
            is Expression.Addition -> a.solve() + b.solve()
            is Expression.Product -> a.solve() * b.solve()
            is Expression.Parentheses -> a.solve()
        }
    }

    override fun step1(): Long {
        input.forEach { expression ->
            val parsed = expression.parse()
            println(parsed)
            println(parsed.solve())
        }
        TODO("Not yet implemented")
    }

    override fun step2(): Long {
        TODO("Not yet implemented")
    }
}