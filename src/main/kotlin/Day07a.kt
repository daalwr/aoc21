import java.io.File
import java.lang.Math.abs

fun main(args: Array<String>) {
    val lines: List<String> = File("src/main/kotlin/input07.txt").readLines()

    val crabs = lines.first().split(",").map { it.toInt() }

    val max = crabs.maxOrNull()!!.toInt()
    val min = crabs.minOrNull()!!.toInt()

    var minSteps = Int.MAX_VALUE

    for (candidatePosition in min..max) {
        var totalDistance = 0
        for (crab in crabs) {
            totalDistance += abs(candidatePosition - crab)
        }

        if (totalDistance < minSteps) {
            minSteps = totalDistance
        }

    }
    println(minSteps)

}
