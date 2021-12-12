import java.io.File

fun main(args: Array<String>) {
    val lines: List<String> = File("src/main/kotlin/input12.txt").readLines()

    val map = mutableMapOf<String, List<String>>()

    lines.forEach { line ->
        run {
            val pair = line.split("-")
            val from = pair.first()
            val to = pair.last()

            if (map.containsKey(from)) {
                map.replace(from, map.getOrDefault(from, listOf()) + to)
            } else {
                map[from] = listOf(to)
            }

            if (map.containsKey(to)) {
                map.replace(to, map.getOrDefault(to, listOf()) + from)
            } else {
                map[to] = listOf(from)
            }
        }
    }

    fun recursiveStep(currentPath: List<String>, smallCaveVisitedTwice: Boolean): List<List<String>> {
        val currentPlace = currentPath.first()

        if (currentPlace == "end") {
            return listOf(currentPath)
        }

        val possibleNextSteps = map.getOrDefault(currentPlace, listOf())

        val filteredNextSteps =
            if (smallCaveVisitedTwice) {
                possibleNextSteps.filterNot { nextStep -> nextStep == "start" || (currentPath.contains(nextStep) && nextStep.lowercase() == nextStep) }
            } else {
                possibleNextSteps.filterNot { nextStep -> nextStep == "start" }
            }

        return filteredNextSteps.flatMap { step ->
            if (smallCaveVisitedTwice) {
                recursiveStep(listOf(step) + currentPath, true)
            } else {
                if (step == step.lowercase() && currentPath.contains(step)) {
                    recursiveStep(listOf(step) + currentPath, true)
                } else {
                    recursiveStep(listOf(step) + currentPath, false)
                }
            }
        }

    }

    println(recursiveStep(listOf("start"), false).size)

}
