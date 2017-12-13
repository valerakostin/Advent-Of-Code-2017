package day13

import utils.getLinesFromResource

sealed class Position(val range: Int = 0) {
    abstract fun isClosed(time: Int): Boolean
}

class ValidPosition(val depth: Int, range: Int) : Position(range) {
    override fun isClosed(time: Int) = (time % ((range - 1) * 2)) == 0
}

object EmptyPosition : Position() {
    override fun isClosed(time: Int) = false
}

private fun createPosition(str: String): ValidPosition {
    val items = str.split(":")

    val depth = items[0].trim().toInt()
    val range = items[1].trim().toInt()

    return ValidPosition(depth, range)
}

private fun task1(layers: Array<Position>) {
    val sum = (0 until layers.size)
            .filter { layers[it].isClosed(it) }
            .map { it * layers[it].range }
            .sum()

    println("Task1\n$sum")
}

private fun task2(layers: Array<Position>) {

    var delay = 0
    while (true) {
        val allOpen = (0 until layers.size)
                .all { !layers[it].isClosed(it + delay) }

        if (allOpen) {
            println("Task2\n$delay")
            return
        }
        delay += 1
    }
}


fun solve() {
    val input = getLinesFromResource("InputDay13.txt")
    val positions = input.map { createPosition(it) }.toList()
    val size = positions.last().depth

    val layers = Array<Position>(size + 1) { EmptyPosition }
    positions.forEach { layers[it.depth] = it }

    task1(layers)
    task2(layers)
}

fun main(argc: Array<String>) {
    println("Day13:")
    solve()
}

