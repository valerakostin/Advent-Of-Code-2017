package day24

import utils.getLinesFromResource

data class Interval(val start: Int, val end: Int) {
    fun weight() = start + end
    override fun toString() = start.toString() + "-" + end.toString()
}

private fun String.toInterval(): Interval {
    val index = this.indexOf("/")

    val start = this.substring(0 until index)
    val end = this.substring(index + 1)

    val s = start.toInt()
    val e = end.toInt()

    return Interval(s, e)
}

fun buildPaths(currentPath: List<Interval>, portIndex: Int, intervals: Set<Interval>, allPaths: MutableList<List<Interval>>) {

    allPaths.add(currentPath)

    val validConnections = getConnections(portIndex, intervals)

    for (connection in validConnections) {
        val newIntervals = intervals - connection
        val newPath = mutableListOf<Interval>()
        newPath.addAll(currentPath)
        newPath.add(connection)
        val next = if (connection.start == portIndex) connection.end else connection.start
        buildPaths(newPath, next, newIntervals, allPaths)
    }
}

fun getConnections(end: Int, intervals: Set<Interval>): List<Interval> {
    return intervals.filter { it.end == end || it.start == end }.toList()
}

private fun task1(allPaths: MutableList<List<Interval>>) {
    val max = allPaths.map { it.sumBy { it.weight() } }.max()
    println("Task1\n$max")
}

private fun task2(allPaths: MutableList<List<Interval>>) {
    val maxPathSize = allPaths.map { it.size }.max()
    if (maxPathSize != null) {
        val longestPath = allPaths.filter { it.size == maxPathSize }.map { it.sumBy { it.weight() } }.max()
        println("Task2\n$longestPath")
    }
}

private fun solve(intervals: MutableSet<Interval>) {
    val allPaths = mutableListOf<List<Interval>>()
    buildPaths(emptyList(), 0, intervals, allPaths)

    task1(allPaths)
    task2(allPaths)
}
private fun solve() {
    val input = getLinesFromResource("InputDay24.txt")
    val intervals = input.map { it.toInterval() }.toMutableSet()
    solve(intervals)
}

fun main(argc: Array<String>) {
    println("Day24:")
    solve()
}

