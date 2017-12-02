package day1

import utils.getLineFromResource

private fun computeCaptcha1(input: String): Int {
    val maxIndex = input.length - 1
    return (0 until input.length)
            .filter { input[it] == input[(it + 1) % maxIndex] }
            .sumBy { input[it].toDigit() }
}

private fun computeCaptcha2(input: String): Int {
    val half = input.length / 2
    return (0 until half)
            .filter { input[it] == input[half + it] }
            .sumBy { input[it].toDigit() } * 2
}

private const val ASCII_ZERO_CODE = 48
private fun Char.toDigit() = if (isDigit()) toInt() % ASCII_ZERO_CODE else 0

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


