import java.io.File
import java.math.BigInteger

fun main(args: Array<String>) {
    val lines: List<String> = File("src/main/kotlin/input06.txt").readLines()

    val fish = lines.first().split(",").map { it.toBigInteger() }
    var state = Array<BigInteger>(9) { _ -> BigInteger.ZERO }

    for (i in 0..8) {
        state[i] = fish.count { x -> x.toInt() == i }.toBigInteger()
    }

    for (simulationStep in 0 until 256) {
        val newState = Array<BigInteger>(9) { _ -> BigInteger.ZERO }

        newState[8] = state[0]
        newState[7] = state[8]
        newState[6] = state[0] + state[7]
        for (j in 0..5) {
            newState[j] = state[j + 1]
        }

        state = newState
    }

    println(state.fold(BigInteger.ZERO){ acc: BigInteger, x: BigInteger -> acc + x })

}
