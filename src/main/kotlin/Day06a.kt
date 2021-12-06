import java.io.File

fun main(args: Array<String>) {
    val lines: List<String> = File("src/main/kotlin/input06.txt").readLines()

    val fish = lines.first().split(",").map { it.toInt() }
    var state = IntArray(9)

    for (i in 0..8) {
        state[i] = fish.count { x -> x == i }
    }

    for (simulationStep in 0 until 80) {
        val newState = IntArray(9)

        newState[8] = state[0]
        newState[7] = state[8]
        newState[6] = state[0] + state[7]
        for (j in 0..5) {
            newState[j] = state[j + 1]
        }

        state = newState
    }

    println(state.sum())

}
