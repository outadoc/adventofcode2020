package fr.outadoc.aoc.twentytwentytwo

import fr.outadoc.aoc.scaffold.Day
import fr.outadoc.aoc.scaffold.readDayInput

class Day02 : Day<Int> {

    private val input: Sequence<Pair<String, String>> =
        readDayInput()
            .lineSequence()
            .map { line ->
                val (a, b) = line.split(' ')
                a to b
            }

    private fun String.parseShape(): Shape {
        return when (this) {
            "A", "X" -> Shape.Rock
            "B", "Y" -> Shape.Paper
            "C", "Z" -> Shape.Scissors
            else -> error("Invalid input $input")
        }
    }

    enum class Shape(val score: Int) {
        Rock(score = 1),
        Paper(score = 2),
        Scissors(score = 3)
    }

    private data class Round(
        val input: Shape,
        val output: Shape
    )

    private enum class Outcome(val score: Int) {
        Loss(score = 0),
        Draw(score = 3),
        Win(score = 6)
    }

    private fun outcomeFor(opponent: Shape, us: Shape): Outcome =
        when (opponent) {
            Shape.Rock -> when (us) {
                Shape.Rock -> Outcome.Draw
                Shape.Paper -> Outcome.Win
                Shape.Scissors -> Outcome.Loss
            }

            Shape.Paper -> when (us) {
                Shape.Rock -> Outcome.Loss
                Shape.Paper -> Outcome.Draw
                Shape.Scissors -> Outcome.Win
            }

            Shape.Scissors -> when (us) {
                Shape.Rock -> Outcome.Win
                Shape.Paper -> Outcome.Loss
                Shape.Scissors -> Outcome.Draw
            }
        }

    private val Round.score: Int
        get() = output.score + outcomeFor(input, output).score

    override fun step1(): Int = input
        .map { (input, respondWith) ->
            Round(
                input = input.parseShape(),
                output = respondWith.parseShape()
            )
        }
        .sumOf { round -> round.score }

    override val expectedStep1 = 12_772

    private data class Strategy(
        val input: Shape,
        val suggestedOutcome: Outcome
    )

    private fun String.parseOutcome(): Outcome =
        when (this) {
            "X" -> Outcome.Loss
            "Y" -> Outcome.Draw
            "Z" -> Outcome.Win
            else -> error("Invalid input $input")
        }

    private val Strategy.suggestedOutput: Shape
        get() = Shape.values().first { possibleShape ->
            outcomeFor(input, possibleShape) == suggestedOutcome
        }

    override fun step2(): Int = input
        .map { (input, suggestedOutcome) ->
            Strategy(
                input = input.parseShape(),
                suggestedOutcome = suggestedOutcome.parseOutcome()
            )
        }
        .map { strategy ->
            Round(
                input = strategy.input,
                output = strategy.suggestedOutput
            )
        }
        .sumOf { round -> round.score }

    override val expectedStep2: Int = 11_618
}
