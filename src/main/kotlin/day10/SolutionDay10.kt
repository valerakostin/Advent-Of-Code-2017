package day10


import utils.KnotHashGenerator
import utils.getLineFromResource

fun computeKnotTying(lengths: List<Int>, round: Int = 1): IntArray {
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

fun task2(input: String) {
    val hash = KnotHashGenerator.generateHash(input)
    println("Task2\n$hash")
}

private fun solve() {
    val input = getLineFromResource("InputDay10.txt")
    val lengths = input.split(",").map { it.toInt() }
    task1(lengths)
    task2(input)
}

fun main(argc: Array<String>) {

    val v = "a".format("%02x")

    println(v)

    println("Day10:")
    solve()
}
