import java.io.File
import java.lang.Math.abs

fun main(args: Array<String>) {
    val lines: List<String> = File("src/main/kotlin/input08.txt").readLines()

    val numbers = lines
        .flatMap { x ->
            x
                .split("|")
                .last()
                .trim()
                .split(" ")
                .map { y -> y.length }
        }
        .filter { x -> x == 2 || x == 3 || x == 4 || x == 7}
        .size


    println(numbers)
}
