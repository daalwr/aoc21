import java.io.File

fun gridContainsFlashes(grid: Array<Array<Int>>): Boolean {
    return grid.any { row -> row.any { col -> col > 9 } }
}

fun main(args: Array<String>) {
    val lines: List<String> = File("src/main/kotlin/input11.txt").readLines()

    val grid = lines.map { x -> x.toCharArray().map { y -> y.digitToInt() }.toTypedArray() }.toTypedArray()

    var flashes = 0

    for (step in 1..100) {
        for (y in 0 until 10) {
            for (x in 0 until 10) {
                grid[y][x]++
            }
        }

        while (gridContainsFlashes(grid)) {
            for (y in 0 until 10) {
                for (x in 0 until 10) {
                    if (grid[y][x] > 9) {

                        flashes++

                        val hasLeft = x > 0
                        val hasRight = x < 9
                        val hasTop = y > 0
                        val hasBottom = y < 9

                        if (hasLeft && hasTop && grid[y - 1][x - 1] != 0) {
                            grid[y - 1][x - 1]++
                        }

                        if (hasTop && grid[y - 1][x] != 0) {
                            grid[y - 1][x]++
                        }

                        if (hasTop && hasRight && grid[y - 1][x + 1] != 0) {
                            grid[y - 1][x + 1]++
                        }

                        if (hasRight && grid[y][x + 1] != 0) {
                            grid[y][x + 1]++
                        }

                        if (hasRight && hasBottom && grid[y + 1][x + 1] != 0) {
                            grid[y + 1][x + 1]++
                        }

                        if (hasBottom && grid[y + 1][x] != 0) {
                            grid[y + 1][x]++
                        }

                        if (hasBottom && hasLeft && grid[y + 1][x - 1] != 0) {
                            grid[y + 1][x - 1]++
                        }

                        if (hasLeft && grid[y][x - 1] != 0) {
                            grid[y][x - 1]++
                        }

                        grid[y][x] = 0
                    }
                }
            }
        }

    }

    println(flashes)

}
