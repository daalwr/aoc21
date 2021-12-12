import org.graphstream.graph.Graph
import org.graphstream.graph.implementations.SingleGraph
import java.io.File
import java.lang.Integer.max
import java.lang.Thread.sleep


fun main(args: Array<String>) {
    System.setProperty("org.graphstream.ui", "swing");

    val lines: List<String> = File("src/main/kotlin/input12.txt").readLines()

    val styleSheet =
        """graph {text-font: Arial; }
            | node { fill-color: white; text-size: 30; size: 30px; stroke-mode: plain; }
            | node.marked { fill-mode: dyn-plain; fill-color: white,lightskyblue; }
            | node.doublesmallcave { fill-color: yellow; }
            | node.big { size: 50px; }
            | node.start { fill-color: green; size: 50px; }
            | node.end { fill-color: red; size: 50px; }
            | edge { text-size: 30; fill-color: grey; }
            | edge.covered { size: 5; size-mode: dyn-size; text-alignment: along; fill-color: lightskyblue; }
            | """.trimMargin()

    val graph: Graph = SingleGraph("Graph")
    graph.display();
    graph.setStrict(false);
    graph.setAutoCreate(true);
    graph.setAttribute("ui.stylesheet", styleSheet)
    graph.setAttribute("ui.title", "0")

    val map = mutableMapOf<String, List<String>>()

    lines.forEach { line ->
        run {
            val pair = line.split("-")
            val from = pair.first()
            val to = pair.last()

            graph.addEdge(from + to, from, to)

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


    graph.nodes().forEach { node ->
        run {
            node.setAttribute("layout.weight", 10F)
            node.setAttribute("ui.label", node.id)
            if (node.id != node.id.lowercase()) {
                node.setAttribute("ui.class", "big")
            }
            if (node.id == "start") {
                node.setAttribute("ui.class", "start")
            }
            if (node.id == "end") {
                node.setAttribute("ui.class", "end")
            }
        }
    }

    fun recursiveStep(currentPath: List<String>, smallCaveVisitedTwice: Boolean): List<List<String>> {

        sleep(30)

        graph.edges().forEach { edge ->
            run {
                edge.setAttribute("ui.label", "")

                var coveredCount = 0

                for (i in 0 until currentPath.size - 1) {
                    if (currentPath[i] == edge.sourceNode.id && currentPath[i + 1] == edge.targetNode.id || currentPath[i + 1] == edge.sourceNode.id && currentPath[i] == edge.targetNode.id) {
                        coveredCount++
                        edge.setAttribute(
                            "ui.label",
                            """${(currentPath.size - i - 1)} ${edge.getAttribute("ui.label")}"""
                        )
                    }
                }

                if (coveredCount > 0) {
                    edge.setAttribute("ui.class", "covered")
                    edge.setAttribute("ui.size", coveredCount)
                } else {
                    edge.setAttribute("ui.class", "")
                    edge.setAttribute("ui.size", 1)
                }
            }
        }

        val countLookup = mutableMapOf<String, Int>()

        var max = 0
        currentPath.forEach { node ->
            run {
                val seen = countLookup.getOrDefault(node, 0) + 1
                countLookup[node] = seen
                max = max(max, seen)
            }
        }

        graph.nodes().forEach { node ->
            run {
                if (node.id == "end") {
                    if (node.id == currentPath.first()) {
                        node.setAttribute("ui.class", "marked, end")
                    } else {
                        node.setAttribute("ui.class", "end")
                    }
                }
                if (node.id != "start" && node.id != "end") {
                    val count = currentPath.count { x -> x == node.id }

                    if (node.id == node.id.lowercase() && count == 2) {
                        node.setAttribute("ui.class", "doublesmallcave")
                    } else {
                        if (currentPath.contains(node.id)) {
                            if (node.id != node.id.lowercase()) {
                                node.setAttribute("ui.class", "big, marked")
                            } else {
                                node.setAttribute("ui.class", "marked")
                            }
                        } else {
                            if (node.id != node.id.lowercase()) {
                                node.setAttribute("ui.class", "big")
                            }
                        }
                    }
                }
                if (currentPath.contains(node.id)) {
                    node.setAttribute(
                        "ui.color",
                        Math.pow(((0.0F + countLookup[node.id]!!).toDouble()), 2.0).toFloat() / (max * max)
                    )
                }
            }
        }

        val currentPlace = currentPath.first()

        if (currentPlace == "end") {
            graph.setAttribute("ui.title", (graph.getAttribute("ui.title").toString().toInt() + 1).toString())
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

    sleep(10000)
    println(recursiveStep(listOf("start"), false).size)

}
