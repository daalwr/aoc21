import java.io.File

fun main(args: Array<String>) {
    val lines: List<String> = File("src/main/kotlin/input01.txt").readLines()
    val numbers: List<Int> = lines.map { x -> x.toInt() }

    var count = 0

    for(i in 1 until numbers.size) {
        if( numbers[i] > numbers[i - 1] ) {
            count += 1
        }
    }

    println(count)
}
