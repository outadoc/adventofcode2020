package fr.outadoc.aoc.scaffold

val <T> List<T>.permutations: Set<List<T>>
    get() {
        fun <T> recurse(list: List<T>): Set<List<T>> {
            if (list.isEmpty()) return setOf(emptyList())
            return list.indices
                .flatMapIndexed { i, _ ->
                    recurse(list - list[i]).map { item -> item + list[i] }
                }
                .toSet()
        }

        return recurse(toList())
    }

fun Iterable<Int>.product() = fold(1) { acc, i -> acc * i }
fun Iterable<Long>.product() = fold(1L) { acc, i -> acc * i }

fun List<Int>.median() = (this[size / 2] + this[(size - 1) / 2]) / 2
fun List<Long>.median() = (this[size / 2] + this[(size - 1) / 2]) / 2

fun <T> Iterable<Set<T>>.intersectAll() = reduce { acc, current -> acc.intersect(current) }
