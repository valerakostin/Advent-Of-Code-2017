package day23

import utils.getLinesFromResource


private sealed class Command
private class Set(val x: String, val y: String) : Command()
private class Sub(val x: String, val y: String) : Command()
private class Mul(val x: String, val y: String) : Command()
private class Jnz(val x: String, val y: String) : Command()

private class Processor(private val commands: List<Command>) {
    private var counter = 0
    var registers = mutableMapOf<String, Long>()

    var multiplications = 0
    fun executeProgram() {
        while (counter >= 0 && counter < commands.size) {
            val command = commands[counter]
            when (command) {
                is Set -> registers[command.x] = getValue(command.y)
                is Mul -> {
                    registers[command.x] = registers.getOrDefault(command.x, 0) * getValue(command.y)
                    multiplications++
                }
                is Sub -> registers[command.x] = registers.getOrDefault(command.x, 0) - getValue(command.y)
                is Jnz -> {
                    val offset = getValue(command.y) - 1
                    val value = getValue(command.x)

                    if (value.toInt() != 0)
                        counter += offset.toInt()
                }
            }
            counter++
        }
    }

    private fun getValue(source: String): Long {
        fun String.isRegister() = this[0].isLetter()
        return if (source.isRegister())
            registers.getOrDefault(source, 0)
        else
            source.toLong()
    }
}

private fun parseCommands(input: List<String>): List<Command> {
    return input.map { parseCommand(it) }.toList()
}

private fun parseCommand(str: String): Command {
    val items = str.split(" ")
    return when (items[0]) {
        "set" -> Set(items[1], items[2])
        "sub" -> Sub(items[1], items[2])
        "mul" -> Mul(items[1], items[2])
        "jnz" -> Jnz(items[1], items[2])
        else -> throw IllegalArgumentException(str)
    }
}

private fun solve() {
    val input = getLinesFromResource("InputDay23.txt")
    val commands = parseCommands(input)

    task1(commands)
    task2()
}

private fun task1(commands: List<Command>) {
    val processor = Processor(commands)
    processor.executeProgram()
    println("Task1\n${processor.multiplications}")
}

private fun task2() {
    var b = 67 * 100 + 100000
    val c = b + 17000
    var h = 0
    while (true) {
        var prime = true
        for (i in 2..b / 2) {
            if (b % i == 0) {
                prime = false
                break
            }
        }
        if (!prime) {
            h += 1
        }
        if (b == c) {
            break
        }
        b += 17
    }
    println("Task2\n$h")
}

fun main(argc: Array<String>) {
    println("Day23:")
    solve()

}


