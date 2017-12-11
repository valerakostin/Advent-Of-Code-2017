package day11

import utils.getLineFromResource

private class HexGrid {
    private var x: Int = 0
    private var y: Int = 0
    private var z: Int = 0

    var maxDistance = 0
    var currentDistance = 0

    private fun move(dx: Int, dy: Int, dz: Int) {
        x += dx
        y += dy
        z += dz

        currentDistance = maxOf(x, y, z)
        maxDistance = maxOf(maxDistance, currentDistance)
    }

    fun move(steps: List<String>) {
        for (step in steps) {
            when (step) {
                "n" -> move(0, 1, -1)
                "s" -> move(0, -1, 1)
                "ne" -> move(1, 0, -1)
                "sw" -> move(-1, 0, 1)
                "nw" -> move(-1, 1, 0)
                "se" -> move(1, -1, 0)
            }
        }
    }
}

fun main(argc: Array<String>) {
    println("Day11:")

    val moves = getLineFromResource("InputDay11.txt").split(",")
    val grid = HexGrid()
    grid.move(moves)

    println("Task1\n${grid.currentDistance}\nTask2\n${grid.maxDistance}")
}

