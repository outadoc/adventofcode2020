package fr.outadoc.aoc.twentytwenty

import fr.outadoc.aoc.scaffold.Day
import fr.outadoc.aoc.scaffold.Year

class Day21 : Day(Year.TwentyTwenty) {

    private data class Food(val ingredients: List<String>, val allergens: List<String>)

    private val foodRegex = Regex("^([a-z ]+) \\(contains ([a-z, ]+)\\)$")

    private val foodz: List<Food> =
        readDayInput()
            .lines()
            .map { line ->
                foodRegex.find(line)!!.groupValues.let { groups ->
                    Food(
                        ingredients = groups[1].split(' '),
                        allergens = groups[2].split(", ")
                    )
                }
            }

    private val initialState = foodz.fold(State()) { state, food ->
        state.copy(
            possibleAllergensPerIngredient =
            state.possibleAllergensPerIngredient +
                food.ingredients.map { ingredient ->
                    val possibleExistingAllergens = state.possibleAllergensPerIngredient[ingredient] ?: emptySet()
                    val possibleNewAllergens = food.allergens.filterNot { allergen ->
                        // Remove all allergens that are already present in a food without this ingredient
                        foodz.any { food ->
                            allergen in food.allergens && ingredient !in food.ingredients
                        }
                    }

                    ingredient to (possibleExistingAllergens + possibleNewAllergens)
                }
        )
    }

    private data class State(
        val possibleAllergensPerIngredient: Map<String, Set<String>> = emptyMap()
    )

    override fun step1(): Long {
        return initialState.possibleAllergensPerIngredient
            .filterValues { allergens -> allergens.isEmpty() }
            .map { (ingredient, _) -> ingredient }
            .sumBy { ingredient ->
                foodz.count { food -> ingredient in food.ingredients }
            }.toLong()
    }

    private tailrec fun State.findFinalState(): State {
        if (possibleAllergensPerIngredient.none { (_, allergens) -> allergens.size > 1 }) return this

        val listOfAlreadyKnownAllergens: Set<String> =
            possibleAllergensPerIngredient
                .filterValues { allergens -> allergens.size == 1 }
                .values
                .map { it.first() }
                .toSet()

        val next = copy(
            possibleAllergensPerIngredient = possibleAllergensPerIngredient
                .mapValues { (_, allergens) ->
                    if (allergens.size > 1) {
                        allergens - listOfAlreadyKnownAllergens
                    } else allergens
                }
        )

        return next.findFinalState()
    }

    override fun step2(): Long {
        val finalState = initialState
            .findFinalState()
            .possibleAllergensPerIngredient
            .mapNotNull { (ingredient, allergens) -> allergens.firstOrNull()?.let { ingredient to it } }
            .sortedBy { (_, allergen) -> allergen }
            .joinToString(separator = ",") { (ingredient, _) -> ingredient }

        println(finalState)

        TODO()
    }
}