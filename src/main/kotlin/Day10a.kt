import java.io.File

fun main(args: Array<String>) {
    val lines: List<String> = File("src/main/kotlin/input10.txt").readLines()

    var total = 0

    loop@ for (line in lines) {

        val stack: ArrayDeque<Char> = ArrayDeque()

        for (char in line) {
            when (char) {
                '(' -> stack.addFirst('(')
                '{' -> stack.addFirst('{')
                '[' -> stack.addFirst('[')
                '<' -> stack.addFirst('<')
                ')' -> {
                    val pop = stack.removeFirst()
                    if (pop != '(') {
                        total += 3
                        continue@loop
                    }
                }
                ']' -> {
                    val pop = stack.removeFirst()
                    if (pop != '[') {
                        total += 57
                        continue@loop
                    }
                }
                '}' -> {
                    val pop = stack.removeFirst()
                    if (pop != '{') {
                        total += 1197
                        continue@loop
                    }
                }
                '>' -> {
                    val pop = stack.removeFirst()
                    if (pop != '<') {
                        total += 25137
                        continue@loop
                    }
                }

            }
        }
    }

    println(total)
}
