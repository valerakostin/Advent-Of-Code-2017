package day2

import utils.getLinesFromResource

private fun task1(lines: List<List<Int>>) {
    val sum = lines.map { it.max()!! - it.min()!! }.sum()
    println("Task1 $sum")
}

private fun task2(lines: List<List<Int>>) {

    fun List<Int>.getEvenlyDivisibleResult(): Int {
        for (i in this) {
            for (j in this) {
                if (i != j) {
                    if (i % j == 0)
                        return i / j
                    else if (j % i == 0)
                        return j / i
                }
            }
        }
        return 0
    }

    val sum = lines.map { it.getEvenlyDivisibleResult() }
            .sum()
    println("Task2 $sum")
}


private fun solve() {

    fun List<String>.convertToIntList(): List<Int> {
        return map { it.toInt() }
    }

    val items = getLinesFromResource("InputDay2.txt")
            .map { it.split("\\s".toRegex()) }
            .map { it.convertToIntList() }
            .toList()

    task1(items)
    task2(items)
}


fun main(argc: Array<String>) {
    println("Day2:")
    solve()
}
