//import java.io.File
//
//sealed interface SnailFishNode {
//    var parent: SnailFishPair?
//}
//
//data class SnailFishPair(
//    var depth: Int,
//    var left: SnailFishNode,
//    var right: SnailFishNode,
//    override var parent: SnailFishPair?
//) : SnailFishNode {
//    override fun toString(): String {
//        return "[${left}, $right]"
//    }
//}
//
//data class SnailFishNumber(
//    var amount: Int, var prev: SnailFishNumber?, var next: SnailFishNumber?,
//    override var parent: SnailFishPair?
//) : SnailFishNode {
//    override fun toString(): String {
//        return "$amount" // + (${prev?.amount}, ${next?.amount})
//    }
//}
//
//fun main(args: Array<String>) {
//
//    val lines: List<String> = File("src/main/kotlin/input18.txt").readLines()
//
//    fun parse(input: String): SnailFishNode {
//
//        var pointer = 0
//        var depth = 0
//
//        val list = mutableListOf<SnailFishNumber>()
//
//        fun getNode(): SnailFishNode {
//            return if (input[pointer] == '[') {
//                pointer++
//                depth++
//                val left = getNode()
////                println("Found left")
//                pointer++
//                val right = getNode()
////                println("Found right")
//                pointer++
//                val result = SnailFishPair(depth, left, right, null)
//                left.parent = result
//                right.parent = result
//                depth--
//                return result
//            } else {
//                val amount = input[pointer].digitToInt()
////                println("Found amount $amount")
//                pointer++
//                val result = SnailFishNumber(amount, null, null, null)
//                list.add(result)
//                return result
//            }
//        }
//
//
//        val result = getNode()
//
//        for (i in 0 until list.size - 1) {
//            list[i].next = list[i + 1]
//            list[i + 1].prev = list[i]
//        }
//
//        return result
//
//    }
//
//    val parsedSnailFishPair = parse(lines[0])
//
//    fun reduce(input: SnailFishNode) {
//        fun searchForExplosion(input: SnailFishNode): Boolean {
//            var explosionHappened = false
//
//            fun explodeStep(input: SnailFishNode, leftSide: Boolean) {
//
//                if (explosionHappened) {
//                    return
//                }
//
//                if (input is SnailFishPair) {
//                    if (input.left is SnailFishNumber && input.right is SnailFishNumber && input.depth > 4) {
//
//                        explosionHappened = true
//
//                        val left = (input.left as SnailFishNumber).amount
//                        val right = (input.right as SnailFishNumber).amount
//
////                        println("Found explosion at $left $right")
//
//                        val newNumber = SnailFishNumber(
//                            0,
//                            (input.left as SnailFishNumber).prev,
//                            (input.right as SnailFishNumber).next,
//                            input.parent
//                        )
//
//
//                        if ((input.left as SnailFishNumber).prev != null) {
//                            (input.left as SnailFishNumber).prev!!.amount =
//                                (input.left as SnailFishNumber).prev!!.amount + left
//                            newNumber.prev!!.next = newNumber
//                        }
//
//                        if ((input.right as SnailFishNumber).next != null) {
//                            (input.right as SnailFishNumber).next!!.amount =
//                                (input.right as SnailFishNumber).next!!.amount + right
//                            newNumber.next!!.prev = newNumber
//                        }
//
////                    println("New number points to ${newNumber.prev} and ${newNumber.next}")
////                    println("${newNumber.prev} points to ${newNumber.prev?.next}. ${newNumber.next} points to ${newNumber.next?.prev}")
//
//                        if (leftSide) {
//                            input.parent!!.left = newNumber
//                        } else {
//                            input.parent!!.right = newNumber
//                        }
//
//                        return
//                    }
//
//                    if (input.left is SnailFishPair) {
//                        explodeStep(input.left, true)
//                    }
//
//                    if (input.right is SnailFishPair) {
//                        explodeStep(input.right, false)
//                    }
//                }
//
//            }
//
//            explodeStep((input as SnailFishPair).left, true)
//            explodeStep((input as SnailFishPair).right, false)
//
//            return explosionHappened
//        }
//
//
//        fun searchForSplits(input: SnailFishNode): Boolean {
//            var splitHappened = false
//
//
//            fun splitStep(input: SnailFishNode, leftSide: Boolean) {
//                if (splitHappened) {
//                    return
//                }
//
//                if (input is SnailFishNumber && input.amount >= 10) {
//                    splitHappened = true
////                    println("Found split at ${input.amount}")
//
////                println("prev is ${input.prev}")
////                println("next is ${input.next}")
//
//                    val left = SnailFishNumber(input.amount / 2, null, null, null)
//                    val right = SnailFishNumber(input.amount / 2 + input.amount % 2, null, null, null)
//
//                    val newPair = SnailFishPair(input.parent!!.depth + 1, left, right, input.parent)
//
//                    left.parent = newPair
//                    left.prev = input.prev
//                    if (left.prev != null) {
//                        left.prev!!.next = left
//                    }
//
//                    left.next = right
//
//                    right.parent = newPair
//                    right.next = input.next
//                    if (right.next != null) {
//                        right.next!!.prev = right
//                    }
//
//                    right.prev = left
//
//                    if (leftSide) {
//                        input.parent!!.left = newPair
//                    } else {
//                        input.parent!!.right = newPair
//                    }
//
//                }
//
//                if (input is SnailFishPair) {
//                    splitStep(input.left, true)
//                    splitStep(input.right, false)
//                }
//            }
//
//            splitStep((input as SnailFishPair).left, true)
//            splitStep((input as SnailFishPair).right, false)
//
//            return splitHappened
//        }
//
//        var checking = true
//
//        while (checking) {
//            val exploded = searchForExplosion(input)
//            if (exploded) {
////                println(input)
//                continue
//            }
//
//            val split = searchForSplits(input)
//            if (split) {
////                println(input)
//                continue
//            }
//
//            checking = false
//        }
//    }
//
//    var root = parse(lines[0])
//
//    fun getLast(n: SnailFishNode): SnailFishNumber {
//        return when (n) {
//            is SnailFishPair -> getLast(n.right)
//            is SnailFishNumber -> n
//        }
//    }
//
//    fun getFirst(n: SnailFishNode): SnailFishNumber {
//        return when (n) {
//            is SnailFishPair -> getFirst(n.left)
//            is SnailFishNumber -> n
//        }
//    }
//
//
//    for (i in 1 until lines.size) {
//        val nextLine = parse(lines[i])
//
//        // Join the boundary
//        val lastInCurrent = getLast(root)
//        val firstInNext = getFirst(nextLine)
//
//        println("$lastInCurrent points to $firstInNext")
//        lastInCurrent.next = firstInNext
//        println("$firstInNext points to $lastInCurrent")
//        firstInNext.prev = lastInCurrent
//
//        val newRoot = SnailFishPair(1, root, nextLine, null)
//        newRoot.left.parent = newRoot
//        newRoot.right.parent = newRoot
//
//        fun setDepths(node: SnailFishNode, depth: Int = 1) {
//            if (node is SnailFishPair) {
////                println("Setting depth $depth")
//                node.depth = depth
//                setDepths(node.left, depth + 1)
//                setDepths(node.right, depth + 1)
//            }
//        }
//
//        root = newRoot
//        setDepths(root)
//
////        println("Before reduction:")
//        println(root)
//        reduce(root)
////        println("After reduction:")
//        println(root)
//    }
//
//    println(root)
//
//    fun calculateMagnitude(node: SnailFishNode): Int {
//        return when(node) {
//            is SnailFishPair -> calculateMagnitude(node.left) * 3 + calculateMagnitude(node.right) * 2
//            is SnailFishNumber -> node.amount
//        }
//    }
//
//
//    println(calculateMagnitude(root))
//}
