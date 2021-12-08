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
