package fr.outadoc.aoc.twentytwenty

import fr.outadoc.aoc.scaffold.Day
import fr.outadoc.aoc.scaffold.Year

class Day22 : Day(Year.TwentyTwenty) {

    private data class Player(val name: String, val deck: List<Int>)
    private data class Round(val players: List<Player>)

    private data class RoundResult(val round: Round, val winner: Player? = null)
    private data class GameResult(val winner: Player?)

    private val initialRound: Round =
        readDayInput()
            .split("\n\n")
            .map { it.lines() }
            .map { playerDeck ->
                Player(
                    name = playerDeck.first().replace(":", ""),
                    deck = playerDeck.drop(1).map { it.toInt() }
                )
            }
            .let { players ->
                Round(players = players)
            }

    private fun Player.play(): Pair<Int?, Player> {
        return when (val card = deck.firstOrNull()) {
            null -> null to this
            else -> card to copy(deck = deck.drop(1))
        }
    }

    private val Player.score: Long
        get() = deck.reversed().mapIndexed { index, card -> (index + 1) * card.toLong() }.sum()

    private tailrec fun RoundResult.findGameResult(): GameResult {
        if (round.players.count { player -> player.deck.isEmpty() } == round.players.size - 1)
            return GameResult(winner = winner)

        return round.playRound(recursive = false).findGameResult()
    }

    private fun Round.playRound(recursive: Boolean): RoundResult {
        val decksAfterPlay = players.map { player -> player.play() }

        val enoughCardsToRecurse = decksAfterPlay.all { (card, player) ->
            card != null && player.deck.size >= card
        }

        val winner = if (recursive && enoughCardsToRecurse) {
            val recursiveRound = Round(players = decksAfterPlay.map { (card, player) ->
                player.copy(deck = player.deck.take(card!!.toInt()))
            })

            RoundResult(round = recursiveRound).findRecursiveGameResult().winner!!
        } else {
            decksAfterPlay.maxByOrNull { (hand, _) -> hand ?: Int.MIN_VALUE }!!.second
        }

        val cardsInPlay: List<Int> = decksAfterPlay
            .sortedBy { (_, player) -> if (player.name == winner.name) Int.MIN_VALUE else Int.MAX_VALUE }
            .mapNotNull { (card, _) -> card }

        val playersNewDeck = decksAfterPlay
            .map { (_, player) -> player }
            .map { state ->
                when (state.name) {
                    winner.name -> state.copy(deck = state.deck + cardsInPlay)
                    else -> state
                }
            }

        val winnerNewDeck = playersNewDeck.first { it.name == winner.name }

        return RoundResult(
            winner = winnerNewDeck,
            round = copy(players = playersNewDeck)
        )
    }

    private tailrec fun RoundResult.findRecursiveGameResult(previousRoundHashes: List<Int> = emptyList()): GameResult {
        if (hashCode() in previousRoundHashes)
            return GameResult(winner = round.players.first { player -> player.name == "Player 1" })

        val hasAPlayerWon = round.players.count { player -> player.deck.isEmpty() } == round.players.size - 1
        if (hasAPlayerWon) return GameResult(winner = winner)

        return round.playRound(recursive = true)
            .findRecursiveGameResult(previousRoundHashes + hashCode())
    }

    private fun Round.print() {
        players.forEach { player ->
            println("${player.name}'s deck: ${player.deck}")
        }
        println()
    }

    fun step1(): Long {
        return RoundResult(round = initialRound).findGameResult().winner!!.score
    }

    fun step2(): Long {
        return RoundResult(round = initialRound).findRecursiveGameResult().winner!!.score
    }
}