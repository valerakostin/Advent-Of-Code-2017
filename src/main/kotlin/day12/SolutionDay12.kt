package day12

import utils.getLinesFromResource

typealias  Graph = Array<List<Int>>

private fun convertToNodes(input: List<String>): Array<List<Int>> {

    val connections = Array<List<Int>>(input.size) { emptyList() }

    for (line in input) {
        val index = line.indexOf("<->")

        val head = line.substring(0, index - 1).toInt()
        val children = line.substring(index + 3)

        val items = children.split(", ").map { it.trim().toInt() }.toList()
        connections[head] = items
    }
    return connections
}

private class ConnectedComponents(private val graph: Graph) {
    private val visited = BooleanArray(graph.size)
    private val connections = IntArray(graph.size)
    private var counter = 0

    init {
        for (i in 0 until graph.size)
            if (!visited[i]) {
                dfs(i)
                counter++
            }
    }

    private fun dfs(node: Int) {
        if (!visited[node]) {
            visited[node] = true
            connections[node] = counter
            val children = graph[node]
            for (child in children)
                dfs(child)
        }

    }

    fun getConnectionCount(node: Int) = connections.filter { it == node }.count()

    fun getGroupCount() = connections.distinct().count()
}

private fun solve() {
    val input = getLinesFromResource("InputDay12.txt")
    val nodes = convertToNodes(input)
    val cc = ConnectedComponents(nodes)
    val count = cc.getConnectionCount(0)
    println("Task 1\n$count")
    println("Task 2\n${cc.getGroupCount()}")
}

fun main(argc: Array<String>) {
    println("Day12:")
    solve()
}
