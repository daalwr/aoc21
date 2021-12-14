import java.io.File


fun main(args: Array<String>) {

    val lines: List<String> = File("src/main/kotlin/input14.txt").readLines()

    val polymer = lines.first().toCharArray().toList()
    val instructions = lines.filter { x -> x.contains("->") }.map { y ->
        run {
            val list = y.split(" -> ")
            Pair(Pair(list[0][0], list[0][1]), list[1][0])
        }
    }.toMap()

    println(instructions)


    var pairMap = mutableMapOf<Pair<Char, Char>, Long>()

    for (x in 0 until polymer.size - 1) {
        pairMap[Pair(polymer[x], polymer[x + 1])] = pairMap.getOrDefault(Pair(polymer[x], polymer[x + 1]), 1L)
    }

    println(pairMap)


    for (loop in 0 until 40) {
        val startStateMap = pairMap.toMap()
        val startState = pairMap.toList()

        for (pairCount in startState) {

            val instruction = instructions[pairCount.first]

            if (instruction != null) {
                pairMap[pairCount.first] = pairMap[pairCount.first]!! - startStateMap[pairCount.first]!!
                pairMap[Pair(pairCount.first.first, instruction)] =
                    pairMap.getOrDefault(Pair(pairCount.first.first, instruction), 0) + pairCount.second
                pairMap[Pair(instruction, pairCount.first.second)] =
                    pairMap.getOrDefault(Pair(instruction, pairCount.first.second), 0) + pairCount.second
            }

        }

//        println(pairMap)

    }

    var letterScores = mutableMapOf<Char, Long>()

    for (pairCount in pairMap) {
        val pair = pairCount.key
        letterScores[pair.first] = letterScores.getOrDefault(pair.first, 0) + pairCount.value
        letterScores[pair.second] = letterScores.getOrDefault(pair.second, 0) + pairCount.value
    }

    letterScores[polymer.first()] = letterScores[polymer.first()]!! + 1
    letterScores[polymer.last()] = letterScores[polymer.last()]!! + 1

    val finalScores = letterScores.toList().map { x -> x.first to x.second / 2 }.sortedBy { y -> y.second }

    println(finalScores.last().second - finalScores.first().second)

}