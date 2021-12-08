import java.io.File
import java.lang.Math.abs

data class Row(val inputs: List<String>, val outputs: List<String>)

fun calculateOutput(row: Row): Int {

    var top = listOf("a", "b", "c", "d", "e", "f", "g")
    var topLeft = listOf("a", "b", "c", "d", "e", "f", "g")
    var topRight = listOf("a", "b", "c", "d", "e", "f", "g")
    var middle = listOf("a", "b", "c", "d", "e", "f", "g")
    var bottomLeft = listOf("a", "b", "c", "d", "e", "f", "g")
    var bottomRight = listOf("a", "b", "c", "d", "e", "f", "g")
    var bottom = listOf("a", "b", "c", "d", "e", "f", "g")

    var rightSide = listOf<String>()
    var topLeftMiddle = listOf<String>()

    val sortedInputs = row.inputs.sortedBy { it.length }

    for (input in sortedInputs) {
        when (input.length) {
            2 -> {
                topRight = topRight.filter { x -> input.contains(x) }
                bottomRight = bottomRight.filter { x -> input.contains(x) }
            }
            3 -> {
                top = input.toList().map { x -> x + "" }.filterNot { x -> topRight.contains(x) }
                topLeft = topLeft.filterNot { x -> top.contains(x) || topRight.contains(x) }
                middle = middle.filterNot { x -> top.contains(x) || topRight.contains(x) }
                bottomLeft = bottomLeft.filterNot { x -> top.contains(x) || topRight.contains(x) }
                bottom = bottom.filterNot { x -> top.contains(x) || topRight.contains(x) }
            }
            4 -> {
                topLeft = input.toList().map { x -> x + "" }.filter { x -> topLeft.contains(x) }
                middle = input.toList().map { x -> x + "" }.filter { x -> middle.contains(x) }

                bottomLeft = bottomLeft.filter { x -> !topLeft.contains(x) }
                bottom = bottom.filter { x -> !topLeft.contains(x) }

                rightSide = topRight
                topLeftMiddle = topLeft
            }
            5 -> {
                // Check if both top left chars appear
                val intersection = topLeftMiddle.intersect(input.toList().map { x -> x + "" })

                if (intersection.size == 2) {
                    println("FIVE")
                    bottom = bottom.filter { x -> input.contains(x) }
                    bottomRight = bottomRight.filter { x -> input.contains(x) }
                    topRight = topRight.filterNot { x -> input.contains(x) }
                } else {
                    val intersectionTwo = rightSide.intersect(input.toList().map { x -> x + "" })
                    if (intersectionTwo.size == 2) {
                        println("THREE")
                        middle = middle.filter { x -> input.contains(x) }
                        topLeft = topLeftMiddle.filterNot { x -> input.contains(x) }
                        bottomLeft = bottomLeft.filterNot { x -> input.contains(x) }
                        bottom = bottom.filter { x -> input.contains(x) }
                    } else {
                        println("TWO")
                    }
                }
            }
        }
    }

    println(top)
    println(topLeft)
    println(topRight)
    println(middle)
    println(bottomLeft)
    println(bottomRight)
    println(bottom)
    println("-----")


    val zero = listOf(top, topLeft, topRight, bottomLeft, bottomRight, bottom).flatten().sorted().toString()
    val one = listOf(topRight, bottomRight).flatten().sorted().toString()
    val two = listOf(top, topRight, middle, bottomLeft, bottom).flatten().sorted().toString()
    val three = listOf(top, topRight, middle, bottomRight, bottom).flatten().sorted().toString()
    val four = listOf(topLeft, topRight, middle, bottomRight).flatten().sorted().toString()
    val five = listOf(top, topLeft, middle, bottomRight, bottom).flatten().sorted().toString()
    val six = listOf(top, topLeft, middle, bottomRight, bottomLeft, bottom).flatten().sorted().toString()
    val seven = listOf(top, topRight, bottomRight).flatten().sorted().toString()
    val eight = listOf(top, topLeft, topRight, middle, bottomRight, bottomLeft, bottom).flatten().sorted().toString()
    val nine = listOf(top, topLeft, topRight, middle, bottomRight, bottom).flatten().sorted().toString()

    val map = mapOf<String, Int>(
        zero to 0,
        one to 1,
        two to 2,
        three to 3,
        four to 4,
        five to 5,
        six to 6,
        seven to 7,
        eight to 8,
        nine to 9
    )


    val numbers = row.outputs.map { x ->
        val y = x.toCharArray().sorted().toString()
        map.getValue(y)
    }

    return numbers.map { x -> x.toString() }.joinToString("").toInt()
}

fun main(arg: Array<String>) {

    val lines: List<String> = File("src/main/kotlin/input08.txt").readLines()

    val answer = lines.map { x ->

        val list = x.split("|")
        Row(list.first().trim().split(" "), list.last().trim().split(" "))
    }.map { row -> calculateOutput(row) }

    println(answer.sum())

}


