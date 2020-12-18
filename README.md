# outadoc's Advent of Code

![JVM Tests](https://github.com/outadoc/adventofcode/workflows/JVM%20Tests/badge.svg)
![Linux Tests](https://github.com/outadoc/adventofcode/workflows/Linux%20Tests/badge.svg)

Personal Advent of Code solutions written in Kotlin, with [Kotlin/Multiplatform](https://kotlinlang.org/docs/reference/multiplatform.html) tooling.

Obviously these aren't necessarily the optimal solutions, but they're mine. Users beware.

## Where's the code?

[Here,](common/src/commonMain/kotlin/fr/outadoc/aoc) in packages split by year.

The solutions are Kotlin classes that implement the `Day` abstract class.
They expose two steps, and their result is checked from the unit tests (there's no other entry point).

## Running the tests

```
./gradlew allTests
```

Or just [check the CI](https://github.com/outadoc/adventofcode/actions).