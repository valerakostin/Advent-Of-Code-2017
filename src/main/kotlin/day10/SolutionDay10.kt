package day10

import utils.getLineFromResource

private fun computeKnotTying(lengths: List<Int>, round: Int = 1): IntArray {
    val data = IntArray(256) { i -> i }

    var currentPosition = 0
    var skipSize = 0
    repeat(round) {
        for (length in lengths) {
            val middle = length / 2
            val size = data.size

            for (offset in 0 until middle) {
                val i = (currentPosition + offset) % size
                val j = (currentPosition + (length - 1) - offset) % size

                data[i] = data[j].also { data[j] = data[i] }
            }

            currentPosition += (length + skipSize) % size
            skipSize += 1
        }
    }
    return data
}

private fun task1(lengths: List<Int>) {
    val data = computeKnotTying(lengths)
    val checksum = data[0] * data[1]
    println("Task1\n$checksum")
}

private fun task2(input: String) {

    val lengths = input.map { it.toInt() }.toMutableList()
    val additionalSequence = listOf(17, 31, 73, 47, 23)
    lengths.addAll(additionalSequence)

    val data = computeKnotTying(lengths, 64)

    val hash = (0..15)
            .map { data.sliceArray(it * 16..it * 16 + 15) }
            .map {
                it.fold(0)
                { acc, value -> acc xor value }
            }
            .map { Integer.toHexString(it) }
            .joinToString(separator = "") { it.format("%02X") }

    println("Task2\n$hash")
}

private fun solve() {
    val input = getLineFromResource("InputDay10.txt")
    val lengths = input.split(",").map { it.toInt() }
    task1(lengths)
    task2(input)
}

fun main(argc: Array<String>) {
    println("Day10:")
    solve()
}
