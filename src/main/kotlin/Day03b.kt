import java.io.File

fun main(args: Array<String>) {
    val lines: List<String> = File("src/main/kotlin/input03test.txt").readLines()

    val length = lines.first().length

    var oxygenNumbers = lines.toList()
    var co2Numbers = lines.toList()

    for (l in 0 until length) {
        val oxyzeros = oxygenNumbers.map { line -> line.get(l) }.filter { x -> x == '0' }.count()
        val oxyones = oxygenNumbers.map { line -> line.get(l) }.filter { x -> x == '1' }.count()

        val co2zeros = co2Numbers.map { line -> line.get(l) }.filter { x -> x == '0' }.count()
        val co2ones = co2Numbers.map { line -> line.get(l) }.filter { x -> x == '1' }.count()

        if (oxygenNumbers.size > 1) {
            if (oxyones >= oxyzeros) {
                oxygenNumbers = oxygenNumbers.filter { x -> x.get(l) == '1' }
            } else {
                oxygenNumbers = oxygenNumbers.filter { x -> x.get(l) == '0' }
            }
        }

        if (co2Numbers.size > 1) {
            if (co2zeros <= co2ones) {
                co2Numbers = co2Numbers.filter { x -> x.get(l) == '0' }
            } else {
                co2Numbers = co2Numbers.filter { x -> x.get(l) == '1' }
            }
        }

    }

    println(oxygenNumbers.first().toInt(2) * co2Numbers.first().toInt(2))

}
