import java.io.File

fun calculateScore(stack: ArrayDeque<Char>): Long {
    var score = 0L

    while (stack.isNotEmpty()) {

        when (stack.removeFirst()) {
            '(' -> {
                score *= 5
                score += 1
            }
            '[' -> {
                score *= 5
                score += 2
            }
            '{' -> {
                score *= 5
                score += 3
            }
            '<' -> {
                score *= 5
                score += 4
            }
        }
    }

    return score

}

fun main(args: Array<String>) {
    val lines: List<String> = File("src/main/kotlin/input10.txt").readLines()

    var total = 0
    val scores = mutableListOf<Long>()

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

        val score = calculateScore(stack)
        scores.add(score)

    }

    scores.sort()
    println(scores[scores.size / 2])

}
