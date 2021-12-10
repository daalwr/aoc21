import java.io.File
import java.lang.Math.abs

fun main(args: Array<String>) {
    val lines: List<String> = File("src/main/kotlin/input09.txt").readLines()

    println(lines)

    val grid =
        lines.map { line -> line.toCharArray().map { x -> x.digitToInt() }.toTypedArray() }.toTypedArray()

    val height = grid.size
    val width = grid.first().size

    var riskLevel = 0

    for (x in 0 until width) {
        for (y in 0 until height) {

            val cell = grid[y][x]

            val hasLeft = x > 0
            val hasRight = x < width - 1
            val hasTop = y > 0
            val hasBottom = y < height - 1

            if (hasLeft && cell >= grid[y][x-1]) {
                // False
            } else if (hasRight && cell >= grid[y][x+1]) {
                // False
            } else if (hasTop && cell >= grid[y-1][x]) {
                // False
            } else if (hasBottom && cell >= grid[y+1][x]) {
                // False
            } else {
                riskLevel += (1 + grid[y][x])
            }

        }
    }

    println(riskLevel)
}
