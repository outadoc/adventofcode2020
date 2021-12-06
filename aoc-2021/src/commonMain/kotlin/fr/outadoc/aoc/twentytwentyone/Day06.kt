package fr.outadoc.aoc.twentytwentyone

import fr.outadoc.aoc.scaffold.Day
import fr.outadoc.aoc.scaffold.readDayInput

class Day06 : Day<Long> {

    companion object {
        private const val MIN_DAY = 0L
        private const val MAX_DAY = 8L
        private const val RESET_DAY = 6L
    }

    private data class State(val fishCountByDay: Map<Long, Long>) {
        val totalFishCount: Long by lazy { fishCountByDay.values.sum() }
    }

    private val initialMap: Map<Long, Long> = (MIN_DAY..MAX_DAY).associateWith { 0L }

    private val initialState = readDayInput()
        .split(',')
        .map { fish -> fish.toLong() }
        .fold(initialMap) { acc, fish -> acc + (fish to acc.getValue(fish) + 1) }
        .let { State(fishCountByDay = it) }

    private fun State.reduce(): State = copy(
        fishCountByDay = fishCountByDay.mapValues { (day, _) ->
            when (day) {
                RESET_DAY -> fishCountByDay.getValue(day + 1) + fishCountByDay.getValue(MIN_DAY)
                MAX_DAY -> fishCountByDay.getValue(MIN_DAY)
                else -> fishCountByDay.getValue(day + 1)
            }
        }
    )

    private fun State.reduceUntil(day: Long): State {
        return (0 until day).fold(this) { acc, _ -> acc.reduce() }
    }

    override fun step1() = initialState.reduceUntil(day = 80).totalFishCount
    override fun step2() = initialState.reduceUntil(day = 256).totalFishCount

    override val expectedStep1 = 380_612L
    override val expectedStep2 = 1_710_166_656_900L
}
