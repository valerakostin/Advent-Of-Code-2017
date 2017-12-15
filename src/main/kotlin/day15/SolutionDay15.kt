package day15

import utils.getLinesFromResource

class Generator1(private var previous: Long, private val factor: Long, private val modulo: Long = 1) {
    private val divider: Long = 2147483647

    private val zero = 0.toLong()
    fun getNext(): Long {
        while (true) {
            val nextValueA = (previous * factor) % divider
            previous = nextValueA
            if (nextValueA % modulo == zero)
                return nextValueA
        }
    }
}

private fun task2(initA: Long, initB: Long) {
    val g1 = Generator1(initA, 16807, 4)
    val g2 = Generator1(initB, 48271, 8)

    val matches = computeMatchCount(g1, g2, 5000000)
    println("Task2\n$matches")
}

private fun computeMatchCount(g1: Generator1, g2: Generator1, times: Int): Int {
    var matchCount = 0
    repeat(times) {

        val a = g1.getNext()
        val b = g2.getNext()

        val lowBitsA = a and 0xffff
        val lowBitsB = b and 0xffff

        if (lowBitsA == lowBitsB) {
            matchCount += 1
        }
    }
    return matchCount
}

private fun task1(initA: Long, initB: Long) {
    val g1 = Generator1(initA, 16807)
    val g2 = Generator1(initB, 48271)

    val matches = computeMatchCount(g1, g2, 40000000)
    println("Task1\n$matches")
}

private fun solve() {
    val lines = getLinesFromResource("InputDay15.txt")
    val number1 = extractInitialValue(lines[0])
    val number2 = extractInitialValue(lines[1])
    task1(number1, number2)
    task2(number1, number2)
}

private fun extractInitialValue(str: String): Long {
    val index = str.lastIndexOf(' ')
    if (index != -1)
        return str.substring(index + 1).toLong()
    return 0
}

fun main(argc: Array<String>) {
    println("Day15:")
    solve()
}


