package fr.outadoc.aoc.twentytwenty

import fr.outadoc.aoc.scaffold.Day
import fr.outadoc.aoc.scaffold.Year

class Day21 : Day(Year.TwentyTwenty) {

    private data class Food(val name: String, val ingredients: List<String>, val allergens: List<String>)

    private val foodRegex = Regex("^([a-z]+) ([a-z ]+) \\(contains ([a-z, ]+)\\)$")

    private val foodz: List<Food> =
        readDayInput()
            .lines()
            .map { line ->
                foodRegex.find(line)!!.groupValues.let { groups ->
                    Food(
                        name = groups[1],
                        ingredients = groups[2].split(' '),
                        allergens = groups[3].split(", ")
                    )
                }
            }

    override fun step1(): Long {
        println(foodz)
        TODO("Not yet implemented")
    }

    override fun step2(): Long {
        TODO("Not yet implemented")
    }
}