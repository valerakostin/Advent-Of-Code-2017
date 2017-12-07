package day7

import utils.getLinesFromResource

data class Node(
        val name: String,
        val weight: Int,
        val children: List<String>)

data class UnbalancedData(val diff: Int, val nodes: MutableList<Node>)

private fun task1(nodes: List<Node>) {
    val map = getChildParentRelation(nodes)

    var name = nodes.first { !it.children.isEmpty() }.name

    var lastParent = name
    while (name.isNotEmpty()) {
        lastParent = name
        name = map[name].orEmpty()
    }
    println("Task1")
    println(lastParent)
}

private fun getChildParentRelation(nodes: List<Node>): Map<String, String> {
    val childParent = mutableMapOf<String, String>()
    for (node in nodes) {
        val children = node.children
        if (!children.isEmpty()) {
            for (child in children) {
                childParent.put(child, node.name)
            }
        }
    }
    return childParent
}

private fun task2(input: List<Node>) {
    val balancedWeight = computeBalancedWeight(input)
    println("Task2 $balancedWeight")
}


private fun computeBalancedWeight(input: List<Node>): Int {

    val sumMap = computeWeights(input)
    val unbalancedNodes = collectUnbalancedNodes(input, sumMap)

    unbalancedNodes.nodes.sortByDescending { it.weight }
    if (unbalancedNodes.nodes.isNotEmpty())
        return unbalancedNodes.nodes[0].weight + unbalancedNodes.diff
    return 0
}

private fun collectUnbalancedNodes(input: List<Node>, sumMap: MutableMap<String, Int>): UnbalancedData {
    val nodes = mutableMapOf<String, Node>()
    input.forEach { nodes[it.name] = it }
    return collectCandidates(nodes, sumMap)
}

private fun collectCandidates(nodes: Map<String, Node>, sumMap: MutableMap<String, Int>): UnbalancedData {
    val candidates = mutableListOf<Node>()
    var diff: Int? = null
    for (node in nodes.values) {
        if (node.children.isNotEmpty()) {
            val childrenWeights = node.children.map { sumMap[it] }
            val allEqual = childrenWeights.all { it == childrenWeights[0] }

            if (!allEqual) {
                val children = node.children
                val firstNodeWeight = sumMap[node.children[0]]

                for (i in 0 until childrenWeights.size) {
                    val currentNode = nodes[children[i]]
                    if (currentNode != null && firstNodeWeight != childrenWeights[i]) {
                        candidates.add(currentNode)

                        if (diff == null)
                            diff = firstNodeWeight!! - sumMap[currentNode.name]!!
                    }
                }
            }
        }
    }
    return UnbalancedData(diff!!, candidates)
}

private fun computeWeights(input: List<Node>): MutableMap<String, Int> {
    val sumMap = mutableMapOf<String, Int>()
    val partition = input.partition { it.children.isEmpty() }
    partition.first.forEach { sumMap[it.name] = it.weight }

    val todo = mutableListOf<Node>()
    todo.addAll(partition.second)

    val toRemove = mutableListOf<Node>()
    while (todo.isNotEmpty()) {

        toRemove.forEach { todo.remove(it) }
        toRemove.clear()

        for (node in todo) {

            val children = node.children

            val weights = children.mapNotNull { sumMap[it] }.toList()
            if (weights.size == children.size) {
                toRemove.add(node)
                sumMap[node.name] = weights.sum() + node.weight
            }
        }
    }
    return sumMap
}

private fun solve() {
    val input = getLinesFromResource("InputDay7.txt")
    val list = input.map { it.convertToNode() }.toList()

    task1(list)
    task2(list)
}

private fun String.convertToNode(): Node {
    val regex = "(\\w*) \\((\\d*)\\)(?:\\s->\\s)?(.*)?".toRegex()
    val result = regex.matchEntire(this)
    result?.let {
        val (name, weight, childrenString) = result.destructured
        val items = if (childrenString.isNotBlank()) childrenString.split(", ") else emptyList()
        return Node(name, weight.toInt(), items)
    }
    return Node("", 0, emptyList())
}

fun main(argc: Array<String>) {
    println("Day7:")
    solve()
}
