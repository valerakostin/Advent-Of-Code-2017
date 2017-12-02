package day3


fun computeDistanceFor(number: Int): Int {
    var value = 1
    var x = 0
    var y = 0
    var currentStep = 1

    fun computeManhattanDistance() = Math.abs(x) + Math.abs(y)

    while (true) {
        val rightAppend = currentStep % 2 == 1

        x += if (rightAppend) 1 else -1
        value++

        if (value == number)
            return computeManhattanDistance()

        repeat(currentStep)
        {
            y += if (rightAppend) 1 else -1
            value++
            if (value == number)
                return computeManhattanDistance()
        }
        repeat(currentStep)
        {
            x += if (rightAppend) -1 else 1
            value++
            if (value == number)
                return computeManhattanDistance()
        }
        currentStep++
    }
}


fun computeCheckSumBiggerThan(number: Int): Int {
    var value = 1
    var x = 0
    var y = 0
    var currentStep = 1

    data class Position(val x: Int, val y: Int)
    val checkSums = mutableMapOf(Position(x, y) to value)

    fun updateCheckSumCache(): Int {
        fun getCheckSumAt(position: Position): Int {

            var sum = 0
            for (i in -1..1) {
                for (j in -1..1) {
                    val p = Position(position.x + i, position.y + j)
                    sum += checkSums.getOrDefault(p, 0)
                }
            }
            return sum
        }

        val p = Position(x, y)
        val checkSum = getCheckSumAt(Position(x, y))
        checkSums[p] = checkSum
        return checkSum
    }

    while (true) {
        val rightAppend = currentStep % 2 == 1

        x += if (rightAppend) 1 else -1
        value++

        val checkSum = updateCheckSumCache()
        if (checkSum > number)
            return checkSum

        repeat(currentStep)
        {
            y += if (rightAppend) 1 else -1
            value++

            val columnElement = updateCheckSumCache()
            if (columnElement > number)
                return columnElement
        }
        repeat(currentStep)
        {
            x += if (rightAppend) -1 else 1
            value++
            val rowElement = updateCheckSumCache()
            if (rowElement > number)
                return rowElement
        }
        currentStep++
    }
}

private fun task2(number: Int) {
    val checkSum = computeCheckSumBiggerThan(number)
    println("Task2 $checkSum")
}

private fun task1(address: Int) {
    val distance = computeDistanceFor(address)
    println("Task1 $distance")
}

private fun solve() {

    val input = 347991
    task1(input)
    task2(input)
}

fun main(argc: Array<String>) {
    println("Day3:")
    solve()
}

