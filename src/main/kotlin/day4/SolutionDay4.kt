package day4

import utils.getLinesFromResource

typealias Password = List<String>
private fun Password.isValidPassword(): Boolean {
    return size == toSet().size
}

private fun Password.isValidPassword2(): Boolean {
    val sortedSize = map { it.toList().sorted() }
            .map { it.toString() }.
            toSet().size
    return sortedSize == size
}

private fun task1(input: List<Password>) {
    println("Task1")
    val count = computePasswordCount(input) { it.isValidPassword() }
    println(count)
}


private fun computePasswordCount(passwords: List<Password>, filter: (Password) -> Boolean): Int {
    return passwords.filter(filter).count()
}

private fun task2(input: List<Password>) {
    println("Task2")
    val count = computePasswordCount(input) { it.isValidPassword2() }
    println(count)
}

private fun solve() {
    val input = getLinesFromResource("InputDay4.txt")
            .map { it.split("\\s".toRegex()) }
            .toList()
    task1(input)
    task2(input)
}

fun main(argc: Array<String>) {
    println("Day4:")
    solve()
}
