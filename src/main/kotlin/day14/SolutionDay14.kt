package day14

import utils.KnotHashGenerator
import utils.getLineFromResource

private fun task1(hashes: List<String>) {
    val sum = hashes.flatMap { it.toList() }
            .filter { it == '1' }
            .count()
    println("Task1\n$sum")
}

private fun task2(binaryHashes: List<String>) {
    data class Point(val x: Int, val y: Int)

    class Graph(private val positions: MutableSet<Point>) {

        private var counter = 0
        private val visited = mutableSetOf<Point>()

        init {
            compute()
        }

        fun getGroup() = counter

        private fun compute() {
            for (point in positions) {
                if (!visited.contains(point)) {
                    compute(point)
                    counter += 1
                }
            }
        }

        private fun compute(p: Point) {
            if (!visited.contains(p)) {
                visited.add(p)
                val children = getChildren(p)
                for (child in children)
                    compute(child)
            }
        }

        private fun getChildren(p: Point): List<Point> {
            val result = mutableListOf<Point>()

            val p1 = Point(p.x + 1, p.y)
            if (positions.contains(p1))
                result.add(p1)

            val p2 = Point(p.x - 1, p.y)
            if (positions.contains(p2))
                result.add(p2)

            val p3 = Point(p.x, p.y + 1)
            if (positions.contains(p3))
                result.add(p3)

            val p4 = Point(p.x, p.y - 1)
            if (positions.contains(p4))
                result.add(p4)

            return result
        }
    }

    val used = mutableSetOf<Point>()
    for (row in 0 until 128) {
        val binaryHash = binaryHashes[row]
        for (column in 0 until binaryHash.length)
            if (binaryHash[column] == '1')
                used.add(Point(row, column))
    }

    val graph = Graph(used)
    val groupCount = graph.getGroup()

    println("Task 2\n$groupCount")
}

private fun convertToBinaryString(str: String): String {
    return buildString {
        for (i in str) {
            when (i) {
                '0' -> append("0000")
                '1' -> append("0001")
                '2' -> append("0010")
                '3' -> append("0011")
                '4' -> append("0100")
                '5' -> append("0101")
                '6' -> append("0110")
                '7' -> append("0111")
                '8' -> append("1000")
                '9' -> append("1001")
                'a' -> append("1010")
                'b' -> append("1011")
                'c' -> append("1100")
                'd' -> append("1101")
                'e' -> append("1110")
                'f' -> append("1111")
            }
        }
    }
}

private fun solve() {
    val base = getLineFromResource("InputDay14.txt")

    val hashes = (0 until 128)
            .map { base + "-" + it }
            .map { KnotHashGenerator.generateHash(it) }
            .map { convertToBinaryString(it) }
            .toList()

    task1(hashes)
    task2(hashes)
}

fun main(argc: Array<String>) {
    println("Day14:")
    solve()
}
