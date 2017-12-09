package day9

import utils.getLineFromResource

private fun String.solvePuzzle() {
    var garbageBlock = false
    var count = 0
    var index = 0
    var depth = 0
    var sum = 0

    while (index < this.length) {
        val char = this[index]
        if (char == '<') {
            if (garbageBlock)
                count += 1
            else
                garbageBlock = true
        } else if (char == '!')
            index += 1
        else if (char == '>')
            garbageBlock = false
        else if (!garbageBlock) {
            if (char == '{')
                depth += 1
            else if (char == '}') {
                sum += depth
                depth -= 1
            }
        } else {
            count += 1
        }
        index += 1
    }
    println("Task1\n$sum\nTask2\n$count")
}

private fun solve() {
    val input = getLineFromResource("InputDay9.txt")
    input.solvePuzzle()
}

fun main(argc: Array<String>) {
    println("Day9:")
    solve()
}
