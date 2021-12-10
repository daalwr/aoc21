import java.io.File

/**
 *  This is unnecessarily complex as I initially misinterpreted the puzzle.
 *  But at least it works!
 */

fun onlyFlowsIntoBasin(
    grid: Array<Array<Int>>,
    x: Int,
    y: Int,
    height: Int,
    width: Int,
    basinLocations: List<Pair<Int, Int>>
): Boolean {
    val hasLeft = x > 0
    val hasRight = x < width - 1
    val hasTop = y > 0
    val hasBottom = y < height - 1

    if (basinLocations.size == 1) {
        return true
    }

    if (grid[y][x] == 9) {
        return false
    }

    if (hasLeft && grid[y][x - 1] < grid[y][x] && !basinLocations.contains(Pair(x - 1, y))) {
        return false
    }

    if (hasRight && grid[y][x + 1] < grid[y][x] && !basinLocations.contains(Pair(x + 1, y))) {
        return false
    }

    if (hasTop && grid[y - 1][x] < grid[y][x] && !basinLocations.contains(Pair(x, y - 1))) {
        return false
    }

    if (hasBottom && grid[y + 1][x] < grid[y][x] && !basinLocations.contains(Pair(x, y + 1))) {
        return false
    }

    return true
}


fun getBasinLocations(grid: Array<Array<Int>>, x1: Int, y1: Int, height: Int, width: Int): List<Pair<Int, Int>> {
    // Return itself

    var allIdentifiedLocations = listOf(Pair(x1, y1))

    var toInvestigate = listOf(Pair(x1, y1))
    var newPositions = listOf<Pair<Int, Int>>()

    while (toInvestigate.isNotEmpty()) {
        // Investigate each new position

        println("New basin locations to search:")
        println(toInvestigate)

        for (position in toInvestigate) {
            // Find neighbours which only flow into the basin

            val x = position.first
            val y = position.second

            val hasLeft = x > 0
            val hasRight = x < width - 1
            val hasTop = y > 0
            val hasBottom = y < height - 1

            if (hasLeft && grid[y][x - 1] > grid[y][x] && onlyFlowsIntoBasin(
                    grid,
                    x - 1,
                    y,
                    height,
                    width,
                    allIdentifiedLocations
                )
            ) {
                println("$x $y points to LEFT ${x - 1} ${y} with value ${grid[y][x - 1]}")
                newPositions = newPositions + Pair(x - 1, y)
            }

            if (hasRight && grid[y][x + 1] > grid[y][x] && onlyFlowsIntoBasin(
                    grid,
                    x + 1,
                    y,
                    height,
                    width,
                    allIdentifiedLocations
                )
            ) {
                println("$x $y points to RIGHT ${x + 1} ${y} with value ${grid[y][x + 1]}")
                newPositions = newPositions + Pair(x + 1, y)
            }

            if (hasTop && grid[y - 1][x] > grid[y][x] && onlyFlowsIntoBasin(
                    grid,
                    x,
                    y - 1,
                    height,
                    width,
                    allIdentifiedLocations
                )
            ) {
                println("$x $y points to TOP ${x} ${y - 1} with value ${grid[y - 1][x]}")
                newPositions = newPositions + Pair(x, y - 1)
            }

            if (hasBottom && grid[y + 1][x] > grid[y][x] && onlyFlowsIntoBasin(
                    grid,
                    x,
                    y + 1,
                    height,
                    width,
                    allIdentifiedLocations
                )
            ) {
                println("$x $y points to BOTTOM ${x} ${y + 1} with value ${grid[y + 1][x]}")
                newPositions = newPositions + Pair(x, y + 1)
            }

        }

        allIdentifiedLocations = allIdentifiedLocations + newPositions
        toInvestigate = newPositions
        newPositions = listOf()
    }

    return allIdentifiedLocations
}


fun main(args: Array<String>) {
    val lines: List<String> = File("src/main/kotlin/input09.txt").readLines()

    println(lines)

    val grid = lines.map { line -> line.toCharArray().map { x -> x.digitToInt() }.toTypedArray() }.toTypedArray()

    val height = grid.size
    val width = grid.first().size

    var basins = mutableListOf<List<Pair<Int, Int>>>()

    loop@ for (x in 0 until width) {
        for (y in 0 until height) {

            val cell = grid[y][x]

            val hasLeft = x > 0
            val hasRight = x < width - 1
            val hasTop = y > 0
            val hasBottom = y < height - 1

            if (hasLeft && cell >= grid[y][x - 1]) {
                // False
            } else if (hasRight && cell >= grid[y][x + 1]) {
                // False
            } else if (hasTop && cell >= grid[y - 1][x]) {
                // False
            } else if (hasBottom && cell >= grid[y + 1][x]) {
                // False
            } else {
                // Low Point Identified
                println("Found new low point at $x $y with value ${grid[y][x]}")
                val locations = getBasinLocations(grid, x, y, height, width)
                println(locations)
                val set = locations.toSet().toList()
                println(set)
                basins.add(set)
            }

        }
    }

    val top3Basins = basins.sortedByDescending { x -> x.size }.take(3)

    println(top3Basins)
    println(top3Basins.map { x -> x.size })
    println(top3Basins.fold(1) { acc, list -> acc * list.size })


}
