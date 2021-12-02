import java.io.File

fun main(args: Array<String>) {
    val lines: List<String> = File("src/main/kotlin/input02.txt").readLines()

    data class Instruction(val command: String, val amount: Int)

    val instructions = lines.map { x -> Instruction(x.split(" ").first(), x.split(" ").last().toInt()) }

    var depth = 0
    var distance = 0
    var aim = 0

    for(instruction in instructions) {
        when(instruction.command) {
            "down" -> aim += instruction.amount
            "up" -> aim -= instruction.amount
            "forward" -> {
                distance += instruction.amount
                depth += aim * instruction.amount
            }
        }
    }

    println(depth * distance)

}
