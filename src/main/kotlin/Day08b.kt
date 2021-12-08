import java.io.File

data class Row(val inputs: List<String>, val outputs: List<String>)

fun calculateOutput(row: Row): Int {

    val allChars = listOf("a", "b", "c", "d", "e", "f", "g")

    var top = allChars
    var topLeft = allChars
    var topRight = allChars
    var middle = allChars
    var bottomLeft = allChars
    var bottomRight = allChars
    var bottom = allChars

    var rightSidePair = listOf<String>()
    var topLeftMiddlePair = listOf<String>()

    val sortedInputs = row.inputs.sortedBy { it.length }

    for (input in sortedInputs) {
        when (input.length) {
            2 -> {
                // String must be ONE
                // Filter the right side segments to the two possible options
                topRight = topRight.filter { x -> input.contains(x) }
                bottomRight = bottomRight.filter { x -> input.contains(x) }
            }
            3 -> {
                // String must be SEVEN
                // The top segment can be identified from the difference between the SEVEN input and the right side.
                top = input.toList().map { x -> x + "" }.filterNot { x -> topRight.contains(x) }

                // All other segments can be filtered to the 4 remaining possible characters.
                topLeft = topLeft.filterNot { x -> top.contains(x) || topRight.contains(x) }
                middle = middle.filterNot { x -> top.contains(x) || topRight.contains(x) }
                bottomLeft = bottomLeft.filterNot { x -> top.contains(x) || topRight.contains(x) }
                bottom = bottom.filterNot { x -> top.contains(x) || topRight.contains(x) }
            }
            4 -> {
                // String must be FOUR
                // The top left and middle can be further filtered to two possible values based on the input.
                topLeft = input.toList().map { x -> x + "" }.filter { x -> topLeft.contains(x) }
                middle = input.toList().map { x -> x + "" }.filter { x -> middle.contains(x) }

                // The bottom left and bottom therefore can also be filtered to other two possible values.
                bottomLeft = bottomLeft.filter { x -> !topLeft.contains(x) }
                bottom = bottom.filter { x -> !topLeft.contains(x) }

                /**
                 * At this point the situation would look something like this:
                 *
                 * top = ["a"]
                 * topLeft = ["b","c"]
                 * middle = ["b","c"]
                 * topRight = ["d","e"]
                 * bottomRight = ["d","e"]
                 * bottomLeft = ["f","g"]
                 * bottom = ["f","g"]
                 *
                 */

                rightSidePair = topRight
                topLeftMiddlePair = topLeft
            }
            5 -> {
                // We can now identify whether a 5 character input is a 2, 3 or 5.

                val intersectionOfInputAndTopLeftMiddlePair =
                    topLeftMiddlePair.intersect(input.toList().map { x -> x + "" })

                if (intersectionOfInputAndTopLeftMiddlePair.size == 2) {
                    // Must be a 5. We can now filter down the right side segments
                    bottom = bottom.filter { x -> input.contains(x) }
                    bottomRight = bottomRight.filter { x -> input.contains(x) }
                    topRight = topRight.filterNot { x -> input.contains(x) }
                } else {
                    val intersectionOfInputAndRightSidePair =
                        rightSidePair.intersect(input.toList().map { x -> x + "" })
                    if (intersectionOfInputAndRightSidePair.size == 2) {
                        // Must be a 3. We can now filter down the left side segments
                        middle = middle.filter { x -> input.contains(x) }
                        topLeft = topLeftMiddlePair.filterNot { x -> input.contains(x) }
                        bottomLeft = bottomLeft.filterNot { x -> input.contains(x) }
                        bottom = bottom.filter { x -> input.contains(x) }
                    } else {
                        // Must have been a 2
                    }
                }
            }
        }
    }


    fun convertToString(segments: List<List<String>>): String {
        return segments.flatten().sorted().toString()
    }

    val sortedStringToNumber = mapOf<String, Int>(
        convertToString(listOf(top, topLeft, topRight, bottomLeft, bottomRight, bottom)) to 0,
        convertToString(listOf(topRight, bottomRight)) to 1,
        convertToString(listOf(top, topRight, middle, bottomLeft, bottom)) to 2,
        convertToString(listOf(top, topRight, middle, bottomRight, bottom)) to 3,
        convertToString(listOf(topLeft, topRight, middle, bottomRight)) to 4,
        convertToString(listOf(top, topLeft, middle, bottomRight, bottom)) to 5,
        convertToString(listOf(top, topLeft, middle, bottomRight, bottomLeft, bottom)) to 6,
        convertToString(listOf(top, topRight, bottomRight)) to 7,
        convertToString(listOf(top, topLeft, topRight, middle, bottomRight, bottomLeft, bottom)) to 8,
        convertToString(listOf(top, topLeft, topRight, middle, bottomRight, bottom)) to 9
    )


    return row.outputs.map { x ->
        val sortedOutputString = x.toCharArray().sorted().toString()
        sortedStringToNumber.getValue(sortedOutputString)
    }.joinToString("") { x -> x.toString() }.toInt()
}

fun main(arg: Array<String>) {

    val lines: List<String> = File("src/main/kotlin/input08.txt").readLines()

    val answer = lines.map { x ->
        val splitList = x.split("|")
        Row(splitList.first().trim().split(" "), splitList.last().trim().split(" "))
    }.sumOf { row -> calculateOutput(row) }

    println(answer)

}


