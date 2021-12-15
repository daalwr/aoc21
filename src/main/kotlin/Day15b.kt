import java.io.File
import java.util.*
import kotlin.system.measureTimeMillis


fun main(args: Array<String>) {

    fun calc() {

        data class NodeDistance(val location: Pair<Int, Int>, val distance: Int)

        class DistanceComparator : Comparator<NodeDistance> {
            override fun compare(a: NodeDistance, b: NodeDistance): Int {
                return a.distance - b.distance;
            }
        }

        val lines: List<String> = File("src/main/kotlin/input15.txt").readLines()
        val grid = lines.map { line -> line.toCharArray().map { c -> c.digitToInt() }.toTypedArray() }.toTypedArray()

        val width = grid[0].size
        val height = grid.size

        fun calcScore(x: Int, y: Int): Int {
            val originalX = x % width
            val originalY = y % height
            val originalScore = grid[originalY][originalX]

            val xAddition = x / width
            val yAddition = y / height

            var candidate = originalScore + xAddition + yAddition

            while (candidate >= 10) {
                candidate -= 9
            }

            return candidate
        }

        val visitedNodes = mutableSetOf<Pair<Int, Int>>()

        val tentativeDistances = PriorityQueue<NodeDistance>(DistanceComparator())

        tentativeDistances.add(NodeDistance(Pair(0, 0), 0))

        val scoresGrid = Array(height * 5) { x -> Array(width * 5) { y -> Int.MAX_VALUE } }

        while (true) {
            val currentlyVisiting = tentativeDistances.poll()!!
            visitedNodes.add(currentlyVisiting.location)

            val x = currentlyVisiting.location.first
            val y = currentlyVisiting.location.second

            if (currentlyVisiting.location == Pair(width * 5 - 1, height * 5 - 1)) {
                println(currentlyVisiting.distance)
                return;
            }

            // Right
            if (x < width * 5 - 1 && !visitedNodes.contains(Pair(x + 1, y))) {
                val currentDistance = scoresGrid[y][x + 1]
                val newDistance = currentlyVisiting.distance + calcScore(x + 1, y)

                if (currentDistance > newDistance) {
                    tentativeDistances.add(NodeDistance(Pair(x + 1, y), newDistance))
                    scoresGrid[y][x + 1] = newDistance
                }

            }

            // Down
            if (y < height * 5 - 1 && !visitedNodes.contains(Pair(x, y + 1))) {
                val currentDistance = scoresGrid[y + 1][x]
                val newDistance = currentlyVisiting.distance + calcScore(x, y + 1)

                if (currentDistance > newDistance) {
                    tentativeDistances.add(NodeDistance(Pair(x, y + 1), newDistance))
                    scoresGrid[y + 1][x] = newDistance
                }
            }

            // Left
            if (x > 0 && !visitedNodes.contains(Pair(x - 1, y))) {
                val currentDistance = scoresGrid[y][x - 1]
                val newDistance = currentlyVisiting.distance + calcScore(x - 1, y)

                if (currentDistance > newDistance) {
                    tentativeDistances.add(NodeDistance(Pair(x - 1, y), newDistance))
                    scoresGrid[y][x - 1] = newDistance
                }
            }


            // Up
            if (y > 0 && !visitedNodes.contains(Pair(x, y - 1))) {
                val currentDistance = scoresGrid[y - 1][x]
                val newDistance = currentlyVisiting.distance + calcScore(x, y - 1)

                if (currentDistance > newDistance) {
                    tentativeDistances.add(NodeDistance(Pair(x, y - 1), newDistance))
                    scoresGrid[y - 1][x] = newDistance
                }
            }

        }

    }

    calc()


}
