package day17


private fun task1(stepCount: Int) {
    val buffer = mutableListOf(0)

    var currentPosition = 0
    for (i in 1..2017) {
        currentPosition = (currentPosition + stepCount) % buffer.size

        currentPosition += 1
        buffer.add(currentPosition, i)
    }
    val index = buffer.indexOf(2017)
    val valueAfter2017 = buffer[index + 1]
    println("Task1\n$valueAfter2017")
}

private fun task2(stepCount: Int) {
    var currentPosition = 0
    var position1 = 0
    for (i in 1..50_000_000) {
        currentPosition = (currentPosition + stepCount) % i
        currentPosition += 1

        if (currentPosition == 1)
            position1 = i
    }
    println("Task2\n$position1")
}

private fun solve() {
    val stepCount = 370
    task1(stepCount)
    task2(stepCount)
}

fun main(argc: Array<String>) {
    println("Day17:")
    solve()
}


