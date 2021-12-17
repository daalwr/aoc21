//import java.io.File
//
//sealed interface Packet {
//    val version: Int
//    val typeId: Int
//}
//
//data class Literal(override val version: Int, override val typeId: Int, val amount: Long) : Packet
//data class Operator(override val version: Int, override val typeId: Int, val packets: List<Packet>) : Packet
//
//
//fun main(args: Array<String>) {
//
//
//    fun toHex(input: Char): String {
//        when (input) {
//            '0' -> return "0000"
//            '1' -> return "0001"
//            '2' -> return "0010"
//            '3' -> return "0011"
//            '4' -> return "0100"
//            '5' -> return "0101"
//            '6' -> return "0110"
//            '7' -> return "0111"
//            '8' -> return "1000"
//            '9' -> return "1001"
//            'A' -> return "1010"
//            'B' -> return "1011"
//            'C' -> return "1100"
//            'D' -> return "1101"
//            'E' -> return "1110"
//            'F' -> return "1111"
//        }
//        throw Error()
//    }
//
//    val lines: List<String> = File("src/main/kotlin/input16.txt").readLines()
//
//    val transmission: String = lines.first().map { x -> toHex(x) }.joinToString("")
//    var pointer = 0
//
//    fun parse(): Packet {
//        println("New Packet")
//
//        val version = transmission.subSequence(pointer, pointer + 3).toString().toInt(2)
//        pointer += 3
//        println("Version $version")
//
//
//        val typeId = transmission.subSequence(pointer, pointer + 3).toString().toInt(2)
//        pointer += 3
//        println("TypeID $typeId")
//
//
//        if (typeId == 4) {
//            // Literal packet
//
//            var lastTakenLeadingChar = '1'
//            var blocksOfFive = 0
//            var binaryNumber = ""
//
//            while (lastTakenLeadingChar == '1') {
//                blocksOfFive++
//                val fiveBits = transmission.subSequence(pointer, pointer + 5).toString()
//                pointer += 5
//
//                lastTakenLeadingChar = fiveBits[0]
//
//                binaryNumber += fiveBits.takeLast(4)
//                println("Found $binaryNumber")
//            }
//
//            val binaryNumberResult = binaryNumber.toLong(2)
//            println("Binary number is $binaryNumberResult")
//
////            val amountStickingOut = (6 + 5 * blocksOfFive) % 4
////
////            val paddingNeeded = if (amountStickingOut == 0) 0 else 4 - amountStickingOut
////            println("Found a 0. Sticking out is $amountStickingOut. Padding calculation is $paddingNeeded")
////
////            pointer += paddingNeeded
//
//            return Literal(version, typeId, binaryNumberResult)
//        } else {
//            // Operator Packet
//            val lengthTypeId = transmission[pointer]
//            pointer += 1
//            println("lengthTypeId $lengthTypeId")
//
//            if (lengthTypeId == '0') {
//                // LengthTypeId 0
//                val totalLengthInBits = transmission.subSequence(pointer, pointer + 15).toString().toInt(2)
//                pointer += 15
//                println("Total Length in bits $totalLengthInBits")
//
//                val startingPosition = pointer
//                val packets = mutableListOf<Packet>()
//
//                while (pointer < startingPosition + totalLengthInBits) {
//                    val newPacket = parse()
//                    packets.add(newPacket)
//                }
//
//                return Operator(version, typeId, packets)
//
//            } else {
//                // LengthTypeId 1
//                val numberOfSubPackets = transmission.subSequence(pointer, pointer + 11).toString().toInt(2)
//                pointer += 11
//                println("Number of sub packets $numberOfSubPackets")
//
//                val packets = mutableListOf<Packet>()
//
//                while (packets.size < numberOfSubPackets) {
//                    packets.add(parse())
//                }
//
//                return Operator(version, typeId, packets)
//
//            }
//        }
//
//    }
//
//    val parsedPackets = parse()
//
//    fun sumVersionNumbers(input: Packet): Int {
//
//        return when (input) {
//            is Operator -> input.version + input.packets.sumOf { x -> sumVersionNumbers(x) }
//            is Literal -> input.version
//        }
//
//    }
//
//
//
//
//    println(sumVersionNumbers(parsedPackets))
//}
