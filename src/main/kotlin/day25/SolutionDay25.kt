package day25

private var currentNode = Node(0, null, null)

data class Node(var value: Int, var left: Node?, var right: Node?)

private var nextState = "A"

enum class Direction { LEFT, RIGHT }


fun moveToNextState() {
    when (nextState) {
        "A" -> {
            if (currentNode.value == 0) {
                currentNode.value = 1
                move(Direction.RIGHT)
                nextState = "B"
            } else {
                currentNode.value = 0
                move(Direction.LEFT)
                nextState = "C"
            }
        }
        "B" -> {
            if (currentNode.value == 0) {
                currentNode.value = 1
                move(Direction.LEFT)
                nextState = "A"
            } else {
                currentNode.value = 1
                move(Direction.RIGHT)
                nextState = "D"
            }
        }
        "C" -> {
            if (currentNode.value == 0) {
                currentNode.value = 0
                move(Direction.LEFT)
                nextState = "B"
            } else {
                currentNode.value = 0
                move(Direction.LEFT)
                nextState = "E"
            }
        }
        "D" -> {
            if (currentNode.value == 0) {
                currentNode.value = 1
                move(Direction.RIGHT)
                nextState = "A"
            } else {
                currentNode.value = 0
                move(Direction.RIGHT)
                nextState = "B"
            }
        }
        "E" -> {
            if (currentNode.value == 0) {
                currentNode.value = 1
                move(Direction.LEFT)
                nextState = "F"
            } else {
                currentNode.value = 1
                move(Direction.LEFT)
                nextState = "C"
            }
        }
        "F" -> {
            if (currentNode.value == 0) {
                currentNode.value = 1
                move(Direction.RIGHT)
                nextState = "D"
            } else {
                currentNode.value = 1
                move(Direction.RIGHT)
                nextState = "A"
            }
        }
    }
}

private fun move(dir: Direction) {
    if (dir == Direction.RIGHT) {
        var rightNode = currentNode.right
        if (rightNode != null) {
            currentNode = rightNode
        } else {
            rightNode = Node(0, currentNode, null)
            currentNode.right = rightNode
            currentNode = rightNode
        }
    } else {
        var leftNode = currentNode.left
        if (leftNode != null)
            currentNode = leftNode
        else {
            leftNode = Node(0, null, currentNode)
            currentNode.left = leftNode
            currentNode = leftNode
        }
    }
}

private fun computeNumberOf1s(): Int {
    var sum = currentNode.value

    var left = currentNode.left
    while (left != null) {
        sum += left.value
        left = left.left
    }

    var right = currentNode.right
    while (right != null) {
        sum += right.value
        right = right.right
    }
    return sum
}

private fun task1() {
    val stepCount = 12667664
    repeat(stepCount) {
        moveToNextState()
    }
    val result = computeNumberOf1s()
    println("Task1\n$result")
}

private fun solve() {
    task1()
}

fun main(argc: Array<String>) {
    println("Day25:")
    solve()
}
