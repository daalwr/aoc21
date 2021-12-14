import java.io.File


fun main(args: Array<String>) {

    val lines: List<String> = File("src/main/kotlin/input14.txt").readLines()

    var polymer = lines.first().toCharArray().toMutableList()
    val instructions = lines.filter { x -> x.contains("->") }.map { y ->
        run {
            val list = y.split(" -> ")
            Pair(Pair(list[0][0], list[0][1]), list[1][0])
        }
    }.toMap()

    println(instructions)

    for (loop in 0 until 10) {
        var insertionPoint = 0
        while (insertionPoint < polymer.size - 1) {
            val left = polymer[insertionPoint]
            val right = polymer[insertionPoint + 1]

            val instruction = instructions[Pair(left, right)]

            if (instruction != null) {
                polymer.add(insertionPoint + 1, instruction)
                insertionPoint++
            }

            insertionPoint++
        }

        val counts = polymer.groupBy { x -> x }.mapValues { entry -> entry.value.size }.toList().sortedBy { x -> x.second }

        println(counts.last())
        println(counts.first())

        println(counts.last().second - counts.first().second)
    }


}
