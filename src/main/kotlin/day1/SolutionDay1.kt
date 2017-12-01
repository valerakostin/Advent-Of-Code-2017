package day1

import utils.getLineFromResource

private fun computeCaptcha1(input: String): Int {
    val asciiZeroCode = 48
    return (0 until input.length)
            .filter { input[it] == input[(it + 1) % (input.length - 1)] }
            .sumBy { input[it].toInt() % asciiZeroCode }
}

private fun computeCaptcha2(input: String): Int {
    val asciiZeroCode = 48
    return (0 until input.length / 2)
            .filter { input[it] == input[input.length / 2 + it] }
            .sumBy { input[it].toInt() % asciiZeroCode } * 2
}


private fun task1(input: String) {
    val sum = computeCaptcha1(input)
    println("Task1 $sum")
}

private fun task2(input: String) {
    val sum = computeCaptcha2(input)
    println("Task2 $sum")
}

private fun solve() {
    val input = getLineFromResource("InputDay1.txt")
    task1(input)
    task2(input)
}

fun main(argc: Array<String>) {
    println("Day1:")
    solve()
}


