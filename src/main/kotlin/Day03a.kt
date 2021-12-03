import java.io.File

fun main(args: Array<String>) {
    val lines: List<String> = File("src/main/kotlin/input03.txt").readLines()

    val length = lines.first().length

    var gammaRate = ""
    var epsilonRate = ""

    for (l in 0 until length) {
        val zeros = lines.map { line -> line.get(l) }.filter { x -> x == '0' }.count()
        val ones = lines.map { line -> line.get(l) }.filter { x -> x == '1' }.count()

        if(zeros > ones) {
            gammaRate += "0"
            epsilonRate += "1"
        } else {
            gammaRate += "1"
            epsilonRate += "0"
        }

    }

    println(gammaRate.toInt(2) * epsilonRate.toInt(2))

}
