package fr.outadoc.aoc.scaffold

actual class FileReader {

    private val fs = require("fs")
    private val path = require("path")

    actual fun readInput(filename: String): String {
        val filepath = path.join(
            __dirname,
            "..", "..", "..", "..", "..",
            "common",
            "src",
            "commonMain",
            "resources",
            filename
        )

        return fs.readFileSync(filepath, "utf-8") as String
    }
}