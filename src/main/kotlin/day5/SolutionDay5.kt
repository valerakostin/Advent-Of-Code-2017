package day5

import utils.getLinesFromResource

private fun task1(jumps: MutableList<Int>) {
    println("Task1")
    val steps = jump(jumps)
    println(steps)
}

private fun task2(jumps: MutableList<Int>) {
    println("Task2")
    val steps = jump(jumps) { if (it >= 3) -1 else 1 }
    println(steps)
}

private fun jump(jumps: MutableList<Int>, offsetFunction: (Int) -> Int = { 1 }): Int {
    var currentPosition = 0
    var steps = 0
    val range = 0 until jumps.size

    while (currentPosition in range) {
        val offset = jumps[currentPosition]
        jumps[currentPosition] += offsetFunction(offset)
        val jump = currentPosition + offset
        steps++
        currentPosition = jump
    }
    return steps
}

private fun solve() {

    val input = getLinesFromResource("InputDay5.txt")
    val jumps = input.map { it.toInt() }
    task1(jumps.toMutableList())
    task2(jumps.toMutableList())
}

fun main(argc: Array<String>) {
    println("Day5:")
    solve()
}

