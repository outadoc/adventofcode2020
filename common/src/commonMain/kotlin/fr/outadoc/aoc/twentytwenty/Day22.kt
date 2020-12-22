package fr.outadoc.aoc.twentytwenty

import fr.outadoc.aoc.scaffold.Day
import fr.outadoc.aoc.scaffold.Year

class Day22 : Day(Year.TwentyTwenty) {

    private data class PlayerState(val playerName: String, val deck: List<Long>)
    private data class Round(val iteration: Int, val players: List<PlayerState>)

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

    private fun Round.next(): Round {
        val decksAfterPlay = players.map { player -> player.play() }
        val winner = decksAfterPlay.maxByOrNull { (hand, _) -> hand ?: Long.MIN_VALUE }!!.second

        val cardsInPlay: List<Long> = decksAfterPlay
            .mapNotNull { (hand, _) -> hand }
            .sortedDescending()

        return copy(
            iteration = iteration + 1,
            players = decksAfterPlay
                .map { (_, player) -> player }
                .map { state ->
                    when (state) {
                        winner -> state.copy(deck = state.deck + cardsInPlay)
                        else -> state
                    }
                }
        )
    }

    private tailrec fun Round.findLastRound(): Round {
        print()
        if (players.count { player -> player.deck.isEmpty() } == players.size - 1) return this
        return next().findLastRound()
    }

    private fun Round.print() {
        println("round #$iteration")
        players.forEach { player ->
            println("${player.playerName}'s deck: ${player.deck}")
        }
        println()
    }

    fun step1(): Long {
        val lastRound = initialRound.findLastRound()
        lastRound.print()
        val winner = lastRound.players.first { player -> player.deck.isNotEmpty() }
        return winner.score
    }

    fun step2(): Long {
        TODO()
    }
}