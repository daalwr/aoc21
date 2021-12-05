import java.io.File

fun main(args: Array<String>) {
    val lines: List<String> = File("src/main/kotlin/input05.txt").readLines()

    val grid = Array(1000) { Array<Int>(1000) { _ -> 0 } }

    val parsedLines = lines.map { line -> line.replace(" -> ", ",").split(",").map { it.toInt() } }

    for ((x1, y1, x2, y2) in parsedLines) {
        if (x1 == x2) {
            val max = maxOf(y1, y2)
            val min = minOf(y1, y2)

            for (y in min..max) {
                grid[x1][y]++
            }
        } else if (y1 == y2) {
            val max = maxOf(x1, x2)
            val min = minOf(x1, x2)

            for (x in min..max) {
                grid[x][y1]++
            }
        }
    }

    println(grid.flatten().count { x -> x >= 2 })
}
