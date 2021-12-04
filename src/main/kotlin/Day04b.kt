import java.io.File

data class BingoCard2(val numbers: List<List<Int>>) {

    fun isWinner(winningNumbers: List<Int>): Boolean {
        for (row in numbers) {
            val markedNumbers = row.filter { number -> winningNumbers.contains(number) }
            if (markedNumbers.size == 5) {
                return true
            }
        }

        for (column in 0 until 5) {
            val columnNumbers = 0.until(5).map { row -> numbers[row][column] }
            val markedNumbers = columnNumbers.filter { number -> winningNumbers.contains(number) }
            if (markedNumbers.size == 5) {
                return true
            }
        }

        return false
    }

    fun sumOfUnmarkedNumbers(winningNumbers: List<Int>): Int {
        return numbers.flatten().filter { x -> !winningNumbers.contains(x) }.sum()
    }
}


fun main(args: Array<String>) {
    val lines: List<String> = File("src/main/kotlin/input04.txt").readLines()

    val allWinningNumbers = lines.first().split(",").map { x -> x.toInt() }
    val bingoCardCount = (lines.size - 1) / 6
    val bingoCards: MutableList<BingoCard2> = mutableListOf()

    for (bingoCardNumber in 0 until bingoCardCount) {

        val bingoCardLines: MutableList<List<Int>> = mutableListOf()

        for (rowNumber in 0 until 5) {
            val fileLineNumber = 2 + bingoCardNumber * 6 + rowNumber
            val line = lines[fileLineNumber]
            val numberLine = line.trim().split(Regex("\\s+")).map { x -> x.toInt() }
            bingoCardLines.add(numberLine)
        }

        val bingoCard = BingoCard2(bingoCardLines.toList())
        bingoCards.add(bingoCard)
    }

    var losingCard: BingoCard2? = null

    for (numbersDrawn in 1 until allWinningNumbers.size) {

        val chosenNumbers = allWinningNumbers.take(numbersDrawn)
        val losingCards = bingoCards.filter { x -> !x.isWinner(chosenNumbers) }

        if (losingCards.size == 1 && losingCard == null) {
            losingCard = losingCards[0]
        }

        if (losingCards.isEmpty() && losingCard != null) {
            val sumOfUnmarkedNumbers = losingCard.sumOfUnmarkedNumbers(chosenNumbers)
            val lastNumber = chosenNumbers.last()
            println(sumOfUnmarkedNumbers * lastNumber)
            return
        }

    }

}
