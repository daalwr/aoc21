import java.io.File
import java.util.*


fun main(args: Array<String>) {

    data class NodeDistance(val location: Pair<Int, Int>, val distance: Int)

    class DistanceComparator : Comparator<NodeDistance> {
        override fun compare(a: NodeDistance, b: NodeDistance): Int {
            return a.distance - b.distance;
        }
    }


    val lines: List<String> = File("src/main/kotlin/input15.txt").readLines()
    val grid = lines.map { line -> line.toCharArray().map { c -> c.digitToInt() }.toTypedArray() }.toTypedArray()

    val maxX = grid[0].size - 1
    val maxY = grid.size - 1

    val visitedNodes = mutableSetOf<Pair<Int, Int>>()

    val tentativeDistances = PriorityQueue<NodeDistance>(DistanceComparator())

    tentativeDistances.add(NodeDistance(Pair(0, 0), 0))

    while (true) {
        val currentlyVisiting = tentativeDistances.poll()!!
        visitedNodes.add(currentlyVisiting.location)

        val x = currentlyVisiting.location.first
        val y = currentlyVisiting.location.second

        if (currentlyVisiting.location == Pair(maxX, maxY)) {
            println(currentlyVisiting.distance)
            return;
        }

        // Right
        if (x < maxX && !visitedNodes.contains(Pair(x + 1, y))) {
            tentativeDistances.add(NodeDistance(Pair(x + 1, y), currentlyVisiting.distance + grid[y][x + 1]))
        }

        if (y < maxY && !visitedNodes.contains(Pair(x, y + 1))) {
            // Down
            tentativeDistances.add(NodeDistance(Pair(x, y + 1), currentlyVisiting.distance + grid[y + 1][x]))
        }

    }


}
