# outadoc's Advent of Code

[![JVM Tests](https://github.com/outadoc/adventofcode/workflows/JVM%20Tests/badge.svg)](https://github.com/outadoc/adventofcode/actions?query=workflow%3A%22JVM+Tests%22)
[![Linux Tests](https://github.com/outadoc/adventofcode/workflows/Linux%20Tests/badge.svg)](https://github.com/outadoc/adventofcode/actions?query=workflow%3A%22Linux+Tests%22)

Personal Advent of Code solutions written in Kotlin, with [Kotlin/Multiplatform](https://kotlinlang.org/docs/reference/multiplatform.html) tooling.

Obviously these aren't necessarily the optimal solutions, but they're mine. Users beware.

## Where's the code?

Mostly in the `aoc-<year>` modules.

The solutions are Kotlin classes that implement the `Day` abstract class.
They expose two steps, and their result is checked from the unit tests (there's no other entry point).

## Running the tests

```
# Tests for all platforms
./gradlew allTests

# Just for JVM
./gradlew jvmTest
```
