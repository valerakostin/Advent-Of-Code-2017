package day16

import utils.getLineFromResource
import java.util.*

sealed class Operation {
    abstract fun modify(field: MutableList<Char>)
}

private class Spin(private val x: Int) : Operation() {
    override fun modify(field: MutableList<Char>) {
        Collections.rotate(field, x)
    }
}

private class Swap(private val x: Int, private val y: Int) : Operation() {
    override fun modify(field: MutableList<Char>) {
        Collections.swap(field, x, y)
    }
}

private class SwapProgram(private val x: Char, private val y: Char) : Operation() {
    override fun modify(field: MutableList<Char>) {
        val index1 = field.indexOf(x)
        val index2 = field.indexOf(y)
        if (index1 != -1 && index2 != -1)
            Collections.swap(field, index2, index1)
        else
            throw IllegalArgumentException("Unable to find program")
    }
}

private fun collectOperations(list: String): List<Operation> {
    val items = list.split(",")
    val operations = mutableListOf<Operation>()

    for (item in items) {
        if (item.startsWith("s")) {
            val steps = item.substring(1)
            val spinOp = Spin(steps.toInt())
            operations.add(spinOp)
        } else if (item.startsWith("x") || item.startsWith("p")) {
            val steps = item.substring(1)
            val index = steps.indexOf("/")

            val v1 = steps.substring(0, index)
            val v2 = steps.substring(index + 1)

            if (item[0] == 'x') {
                operations.add(Swap(v1.toInt(), v2.toInt()))
            } else
                operations.add(SwapProgram(v1[0], v2[0]))
        } else throw IllegalArgumentException("Unable to process $item")
    }
    return operations
}

private fun List<Char>.asString() = joinToString(separator = "")

private fun collectUniqueEntries(operations: List<Operation>, field: MutableList<Char>): HashSet<String> {
    val cache = linkedSetOf<String>()

    while (true) {
        operations.forEach { it.modify(field) }
        val value = field.asString()
        if (cache.contains(value))
            break
        cache.add(value)
    }
    return cache
}

private fun task1(field: MutableList<Char>, operations: List<Operation>) {
    operations.forEach { it.modify(field) }
    println("Task1\n${field.asString()}")
}

private fun task2(field: MutableList<Char>, operations: List<Operation>) {
    val cache = collectUniqueEntries(operations, field)
    val stepsToDo = (1_000_000_000 % cache.size) - 2

    repeat(stepsToDo) {
        operations.forEach { it.modify(field) }
    }
    println("Task2\n${field.asString()}")
}

private fun solve() {
    val input = getLineFromResource("InputDay16.txt")
    val operations = collectOperations(input)
    val field = mutableListOf('a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p')

    task1(field, operations)
    task2(field, operations)
}

fun main(argc: Array<String>) {
    println("Day16:")
    solve()
}
