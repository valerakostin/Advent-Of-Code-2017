package day19

import utils.getLinesFromResource

data class Position(val x: Int, val y: Int) {
    fun left() = Position(x + 1, y)
    fun right() = Position(x - 1, y)
    fun up() = Position(x, y - 1)
    fun down() = Position(x, y + 1)
}

enum class Direction { DOWN, UP, LEFT, RIGHT }

private fun solveTask(input: List<String>) {

    val initial = input[0].indexOf("|")

    // TODO replace with state object
    var currentPosition = Position(0, initial)
    var previous = currentPosition

    var counter = 0
    var direction = Direction.DOWN

    val buffer = StringBuffer()
    while (true) {
        val down = currentPosition.down()
        val left = currentPosition.left()
        val up = currentPosition.up()
        val right = currentPosition.right()
        when (direction) {
            Direction.DOWN, Direction.UP -> {

                if (direction == Direction.DOWN && isValidPosition(input, down, previous)) {
                    currentPosition = down
                } else if (direction == Direction.UP && isValidPosition(input, up, previous)) {
                    currentPosition = up
                } else if (isValidPosition(input, right, previous)) {
                    currentPosition = right
                    direction = Direction.RIGHT
                } else if (isValidPosition(input, left, previous)) {
                    currentPosition = left
                    direction = Direction.LEFT
                }
            }
            Direction.RIGHT, Direction.LEFT -> {

                if (direction == Direction.RIGHT && isValidPosition(input, right, previous)) {
                    currentPosition = right
                } else if (direction == Direction.LEFT && isValidPosition(input, left, previous)) {
                    currentPosition = left
                } else if (isValidPosition(input, up, previous)) {
                    currentPosition = up
                    direction = Direction.UP
                } else if (isValidPosition(input, down, previous)) {
                    currentPosition = down
                    direction = Direction.DOWN
                }
            }
        }
        val char = input[currentPosition.x][currentPosition.y]
        if (char.isLetter() && currentPosition != previous) {
            buffer.append(char)
        }
        counter++
        if (currentPosition == previous) {
            println("Task1\n$buffer")
            println("Task2\n$counter")
            break
        }
        previous = currentPosition
    }
}

private fun isValidPosition(input: List<String>, current: Position, previous: Position): Boolean {
    return current != previous &&
            current.x >= 0 &&
            current.y >= 0 &&
            current.x < input.size &&
            input[current.x].length > current.y &&
            input[current.x][current.y] != ' '
}

private fun solve() {
    val input = getLinesFromResource("InputDay19.txt")
    solveTask(input)
}

fun main(argc: Array<String>) {
    println("Day19:")
    solve()
}

