import java.io.File

fun main(args: Array<String>) {
    data class CoordinatePair(val x: Int, val y: Int)
    data class FoldInstruction(val direction: String, val location: Int)

    val lines: List<String> = File("src/main/kotlin/input13.txt").readLines()

    val coordinates = lines
        .filter { x -> x.contains(",") }
        .map { y ->
            run {
                val list = y.split(",").map { z -> z.toInt() }
                CoordinatePair(list[0], list[1])
            }
        }

    val folds = lines.filter { x -> x.contains("=") }.map { y ->
        run {
            val string = y.split(" ").last()
            FoldInstruction(string.split("=").first(), string.split("=").last().toInt())
        }
    }

    var newCoordinates = coordinates

    for (f in folds) {

        newCoordinates = newCoordinates.map { (x, y) ->
            run {
                if (f.direction == "x" && x > f.location) {
                    CoordinatePair(f.location - (x - f.location), y)
                } else if (f.direction == "y" && y > f.location) {
                    CoordinatePair(x, f.location - (y - f.location))
                } else {
                    CoordinatePair(x, y)
                }
            }
        }

        newCoordinates = newCoordinates.toSet().toList()
    }

    for (y in 0..20) {
        var line = ""
        for (x in 0..50) {
            line = if (newCoordinates.contains(CoordinatePair(x, y))) {
                "$line#"
            } else {
                "$line."
            }
        }
        println(line)
    }

}
