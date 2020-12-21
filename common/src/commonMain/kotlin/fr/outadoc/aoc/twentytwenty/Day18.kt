package fr.outadoc.aoc.twentytwenty

import fr.outadoc.aoc.scaffold.Day
import fr.outadoc.aoc.scaffold.Year

class Day18 : Day(Year.TwentyTwenty) {

    private sealed class Expression {
        data class Addition(val a: Expression, val b: Expression) : Expression() {
            override fun toString() = "($a + $b)"
        }

        data class Product(val a: Expression, val b: Expression) : Expression() {
            override fun toString() = "($a * $b)"
        }

        data class Parentheses(val a: Expression) : Expression() {
            override fun toString() = "($a)"
        }

        data class Constant(val n: Long) : Expression() {
            override fun toString() = n.toString()
        }
    }

    private val input: Sequence<String> =
        readDayInput()
            .lineSequence()

    private fun parse(rest: List<Char>, previousExpr: Expression? = null): Expression {
        if (rest.isEmpty()) return previousExpr!!

        val head = rest.first()
        val tail = rest.drop(1)

        return when (head) {
            in '0'..'9' -> parse(tail, Expression.Constant(head.toString().toLong()))
            '+' -> Expression.Addition(parse(tail), previousExpr!!)
            '*' -> Expression.Product(parse(tail), previousExpr!!)
            ')' -> {
                val (contentInside, contentOutside) = parseParentheses(tail)
                parse(contentOutside, Expression.Parentheses(parse(contentInside)))
            }
            else -> throw IllegalStateException()
        }
    }

    private fun parseParentheses(content: List<Char>): Pair<List<Char>, List<Char>> {
        var openParens = 0
        content.forEachIndexed { index, c ->
            when (c) {
                ')' -> openParens++
                '(' -> when (openParens) {
                    0 -> return content.subList(0, index) to content.subList(index + 1, content.size)
                    else -> openParens--
                }
            }
        }

        throw IllegalArgumentException("no matching parens")
    }

    private fun String.parse(): Expression =
        parse(replace(" ", "").toCharArray().toList().reversed())

    private fun Expression.solve(): Long = when (this) {
        is Expression.Constant -> n
        is Expression.Addition -> a.solve() + b.solve()
        is Expression.Product -> a.solve() * b.solve()
        is Expression.Parentheses -> a.solve()
    }

    private fun prioritizeAdditionOnce(expr: Expression): Expression = when (expr) {
        is Expression.Addition -> when {
            expr.a is Expression.Product -> Expression.Product(
                a = prioritizeAdditionOnce(expr.a.a),
                b = prioritizeAdditionOnce(Expression.Addition(expr.a.b, expr.b))
            )

            expr.b is Expression.Product -> Expression.Product(
                a = prioritizeAdditionOnce(Expression.Addition(expr.a, expr.b.a)),
                b = prioritizeAdditionOnce(expr.b.b)
            )

            else -> expr.copy(
                a = prioritizeAdditionOnce(expr.a),
                b = prioritizeAdditionOnce(expr.b),
            )
        }

        is Expression.Product -> expr.copy(
            a = prioritizeAdditionOnce(expr.a),
            b = prioritizeAdditionOnce(expr.b)
        )

        is Expression.Parentheses -> expr.copy(
            a = prioritizeAdditionOnce(expr.a)
        )

        is Expression.Constant -> expr
    }

    private fun Expression.prioritizeAddition(): Expression {
        var lastIter: Expression = this
        while (true) {
            val next = prioritizeAdditionOnce(lastIter)
            if (next == lastIter) return next
            lastIter = next
        }
    }

    fun step1(): Long {
        return input.sumOf { expression ->
            expression.parse().solve()
        }
    }

    fun step2(): Long {
        return input.sumOf { expression ->
            expression.parse()
                .prioritizeAddition()
                .solve()
        }
    }
}