package day20

import utils.getLinesFromResource

data class Point(val x: Int, val y: Int, val z: Int) {
    fun distance(): Int = Math.abs(x) + Math.abs(y) + Math.abs(z)
}

data class Position(val p: Point, val v: Point, val a: Point) {
    fun getNext(): Position {
        val newVel = Point(v.x + a.x, v.y + a.y, v.z + a.z)
        val newPos = Point(p.x + newVel.x, p.y + newVel.y, p.z + newVel.z)
        return Position(newPos, newVel, a)
    }
}

private fun task2(input: MutableList<Position>) {
    // brute force
    var positions = input
    // for my input 37 is enough

    (0..100).forEach() {
        positions = positions.map { it.getNext() }.toMutableList()
        val grouped = positions.groupBy { it.p }
        val filtered = grouped.filter { it.value.size > 1 }.flatMap { it.value }
        positions.removeAll(filtered)
    }
    println("Task2\n${positions.size}")
}

private fun task1(positions: List<Position>) {
    val sortedByAcceleration = positions.sortedWith(compareBy({ it.a.distance() }, { it.v.distance() }, { it.p.distance() }))
    val slowest = sortedByAcceleration.first()

    val i = positions.indexOf(slowest)
    println("Task1\n$i")
}

private fun String.toPosition(): Position {
    val index1 = indexOf("p=<") + 3
    val index2 = indexOf(">", index1)

    val pSubstring = substring(index1, index2)
    val items = pSubstring.split(",")
    val pp = Point(items[0].trim().toInt(), items[1].trim().toInt(), items[2].trim().toInt())


    val index3 = indexOf("v=<", index2 + 1) + 3
    val index4 = indexOf(">", index3)

    val vSubstring = substring(index3, index4)
    val items2 = vSubstring.split(",")
    val vp = Point(items2[0].trim().toInt(), items2[1].trim().toInt(), items2[2].trim().toInt())

    val index5 = indexOf("a=<", index4 + 1) + 3
    val index6 = indexOf(">", index5)

    val aSubstring = substring(index5, index6)
    val items3 = aSubstring.split(",")
    val ap = Point(items3[0].trim().toInt(), items3[1].trim().toInt(), items3[2].trim().toInt())

    return Position(pp, vp, ap)

}

private fun solve() {
    val input = getLinesFromResource("InputDay20.txt")

    val positions = input.map { it.toPosition() }.toMutableList()
    task1(positions)
    task2(positions)
}

fun main(argc: Array<String>) {
    println("Day20:")
    solve()
}
