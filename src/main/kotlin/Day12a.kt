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

    fun recursiveStep(currentPath: List<String>): List<List<String>> {
        val currentPlace = currentPath.first()

        if (currentPlace == "end") {
            return listOf(currentPath)
        }

        val possibleNextSteps = map.getOrDefault(currentPlace, listOf())

        val filteredNextSteps =
            possibleNextSteps.filterNot { nextStep -> currentPath.contains(nextStep) && nextStep.lowercase() == nextStep }

        return filteredNextSteps.flatMap { nextStep -> recursiveStep(listOf(nextStep) + currentPath) }
    }

    println(recursiveStep(listOf("start")).size)

}
