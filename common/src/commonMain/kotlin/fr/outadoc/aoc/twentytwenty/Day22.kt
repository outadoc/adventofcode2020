package fr.outadoc.aoc.twentytwenty

import fr.outadoc.aoc.scaffold.Day
import fr.outadoc.aoc.scaffold.Year

class Day22 : Day(Year.TwentyTwenty) {

    private data class PlayerState(val playerName: String, val deck: List<Long>)
    private data class Round(val iteration: Int, val players: List<PlayerState>)

    private data class RoundResult(val round: Round, val winner: PlayerState? = null)
    private data class GameResult(val winner: PlayerState?)

    private val initialRound: Round =
        readDayInput()
            .split("\n\n")
            .map { it.lines() }
            .map { playerDeck ->
                PlayerState(
                    playerName = playerDeck.first().replace(":", ""),
                    deck = playerDeck.drop(1).map { it.toLong() }
                )
            }
            .let { players ->
                Round(iteration = 0, players = players)
            }

    private fun PlayerState.play(): Pair<Long?, PlayerState> {
        return when (val card = deck.firstOrNull()) {
            null -> null to this
            else -> card to copy(deck = deck.drop(1))
        }
    }

    private val PlayerState.score: Long
        get() = deck.reversed().mapIndexed { index, card -> (index + 1) * card }.sum()

    private fun Round.playRound(): RoundResult {
        val decksAfterPlay = players.map { player -> player.play() }

        val winner = decksAfterPlay.maxByOrNull { (hand, _) -> hand ?: Long.MIN_VALUE }!!.second

        val cardsInPlay: List<Long> = decksAfterPlay
            .mapNotNull { (hand, _) -> hand }
            .sortedDescending()

        val playersNewDeck = decksAfterPlay
            .map { (_, player) -> player }
            .map { state ->
                when (state) {
                    winner -> state.copy(deck = state.deck + cardsInPlay)
                    else -> state
                }
            }

        val winnerNewDeck = playersNewDeck.first { it.playerName == winner.playerName }

        return RoundResult(
            winner = winnerNewDeck,
            round = copy(
                iteration = iteration + 1,
                players = playersNewDeck
            )
        )
    }

    private tailrec fun RoundResult.findGameResult(): GameResult {
        round.print()

        if (round.players.count { player -> player.deck.isEmpty() } == round.players.size - 1)
            return GameResult(winner = winner)

        return round.playRound().findGameResult()
    }

    private fun Round.print() {
        println("round #$iteration")
        players.forEach { player ->
            println("${player.playerName}'s deck: ${player.deck}")
        }
        println()
    }

    fun step1(): Long {
        return RoundResult(round = initialRound).findGameResult().winner!!.score
    }

    fun step2(): Long {
        TODO()
    }
}