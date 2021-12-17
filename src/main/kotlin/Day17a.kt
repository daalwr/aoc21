import java.io.File

fun main(args: Array<String>) {


    val lines: List<String> = File("src/main/kotlin/input17.txt").readLines()

    val line = lines.first()

    val xResult = Regex("""x=(-?\d+)\.\.(-?\d+)""").find(line)
    val yResult = Regex("""y=(-?\d+)\.\.(-?\d+)""").find(line)

    val x1 = xResult!!.groupValues[1].toInt()
    val x2 = xResult!!.groupValues[2].toInt()

    val y1 = yResult!!.groupValues[1].toInt()
    val y2 = yResult!!.groupValues[2].toInt()

    println("$x1 $x2 $y1 $y2")

    var highestReached = 0
    var highX = 0
    var highY = 0
    var count = 0


    for (startXVelocity in 1..280) {
        for (startYVelocity in -73..1000) {

//            println("Starting from $startXVelocity $startYVelocity")

            var xVelocity = startXVelocity
            var yVelocity = startYVelocity

            var x = 0
            var y = 0


            var maxY = 0

            if(xVelocity == 0 && x < x1) {
                break
            }

            while (x <= x2 && y >= y1) {

                if (x >= x1 && y <= y2) {
                    // In the target
                    println("Target reached for $startXVelocity $startYVelocity")
                    count++
                    highestReached = maxOf(maxY, highestReached)

                    if(highestReached == maxY) {
                        highX = startXVelocity
                        highY = startYVelocity
                    }
                    break
                }

                // Simulate step
                x += xVelocity
                y += yVelocity
//                println("At $x $y")

                if(xVelocity > 0) xVelocity--
                if(xVelocity < 0) xVelocity++

                yVelocity--

                maxY = maxOf(maxY, y)

            }

        }
    }

    println(highestReached)
}
