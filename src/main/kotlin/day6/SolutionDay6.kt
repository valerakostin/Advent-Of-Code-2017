package day6

import utils.getLineFromResource

class MemoryBank(private val bank: MutableList<Int>) {

    private fun getMaxPair(): Pair<Int, Int> {
        var number: Int = Integer.MIN_VALUE
        var index: Int = -1

        bank.forEachIndexed { idx, i ->
            if (i > number) {
                number = i
                index = idx
            }
        }
        return index to number
    }

    private fun List<Int>.snapShot() = joinToString(separator = ",")

    fun computeStepCount(): Int {
        val cache = mutableListOf(bank.snapShot())
        var count = 0
        while (true) {
            val max = getMaxPair()
            bank[max.first] = 0

            var currentIndex = max.first
            var portion = max.second

            val bankSize = bank.size
            val value = portion / bankSize
            if (value > 0) {
                for (i in 0 until bankSize)
                    bank[i] += value
                portion -= value * bankSize
            }
            for (i in 0 until portion) {
                currentIndex = (currentIndex + 1) % bankSize
                bank[currentIndex] += 1
            }
            count += 1
            val snapShot = snapShot(bank)
            if (cache.contains(snapShot))
                return count
            else
                cache.add(snapShot)
        }
    }
}

private fun snapShot(list: List<Int>) = list.joinToString(separator = ",")

private fun solve() {
    val input = getLineFromResource("InputDay6.txt")
    val memory = input.split("\\s".toRegex()).map { it.toInt() }.toList()

    val memoryBank = MemoryBank(memory.toMutableList())
    println("Task1")
    val result = memoryBank.computeStepCount()
    println(result)
    println("Task2")
    val result2 = memoryBank.computeStepCount()
    println(result2)
}

fun main(argc: Array<String>) {
    println("Day6:")
    solve()
}
