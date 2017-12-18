package day18

import utils.getLinesFromResource
import java.util.*

private sealed class Command
private class Snd(val x: String) : Command()
private class Set(val x: String, val y: String) : Command()
private class Add(val x: String, val y: String) : Command()
private class Mul(val x: String, val y: String) : Command()
private class Mod(val x: String, val y: String) : Command()
private class Rcv(val x: String) : Command()
private class Jgz(val x: String, val y: String) : Command()

private class Processor(private val commands: List<Command>) {
    private var counter = 0
    private var registers = mutableMapOf<String, Long>()

    var lastFrequency = 0

    fun executeProgram() {
        while (counter >= 0 && counter < commands.size) {
            val command = commands[counter]
            when (command) {
                is Snd -> lastFrequency = getValue(command.x).toInt()
                is Set -> registers[command.x] = getValue(command.y)
                is Add -> registers[command.x] = registers.getOrDefault(command.x, 0) + getValue(command.y)
                is Mul -> registers[command.x] = registers.getOrDefault(command.x, 0) * getValue(command.y)
                is Mod -> registers[command.x] = registers.getOrDefault(command.x, 0) % getValue(command.y)
                is Rcv -> {
                    val v = getValue(command.x)
                    if (v.toInt() != 0)
                        return
                }
                is Jgz -> {
                    val offset = getValue(command.y) - 1
                    val value = getValue(command.x)

                    if (value > 0)
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

private class Processor2(id: Int, private val commands: List<Command>, private val channelIn: Queue<Long>, private val channelOut: Queue<Long>) {
    private var counter = 0
    private var registers = mutableMapOf("p" to id.toLong())
    var sendCounter = 0

    var waiting = false
    fun nextCommand() {

        if (isNotReady()) {
            val command = commands[counter]
            waiting = false
            when (command) {
                is Set -> registers[command.x] = getValue(command.y)
                is Add -> registers[command.x] = registers.getOrDefault(command.x, 0) + getValue(command.y)
                is Mul -> registers[command.x] = registers.getOrDefault(command.x, 0) * getValue(command.y)
                is Mod -> registers[command.x] = registers.getOrDefault(command.x, 0) % getValue(command.y)
                is Jgz -> {
                    val offset = getValue(command.y) - 1
                    val value = getValue(command.x)

                    if (value > 0)
                        counter += offset.toInt()
                }
                is Rcv -> {
                    if (!channelIn.isEmpty()) {
                        val value = channelIn.poll()
                        registers[command.x] = value

                    } else {
                        counter--
                        waiting = true
                    }
                }
                is Snd -> {
                    channelOut.add(getValue(command.x))
                    sendCounter++
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

    private fun isNotReady() = (counter >= 0 && counter < commands.size)
}


private fun parseCommands(input: List<String>): List<Command> {
    return input.map { parseCommand(it) }.toList()
}

private fun parseCommand(str: String): Command {
    val items = str.split(" ")
    return when (items[0]) {
        "snd" -> Snd(items[1])
        "set" -> Set(items[1], items[2])
        "add" -> Add(items[1], items[2])
        "mul" -> Mul(items[1], items[2])
        "mod" -> Mod(items[1], items[2])
        "rcv" -> Rcv(items[1])
        "jgz" -> Jgz(items[1], items[2])
        else -> throw IllegalArgumentException(str)
    }
}

private fun solve() {
    val input = getLinesFromResource("InputDay18.txt")
    val commands = parseCommands(input)

    task1(commands)
    task2(commands)
}

private fun task1(commands: List<Command>) {
    val processor = Processor(commands)
    processor.executeProgram()
    println("Task1\n${processor.lastFrequency}")
}

private fun task2(commands: List<Command>) {
    println("Task2")

    val channel0In = LinkedList<Long>()
    val channel1In = LinkedList<Long>()
    val processor1 = Processor2(0, commands, channel0In, channel1In)
    val processor2 = Processor2(1, commands, channel1In, channel0In)

    while (true) {
        processor1.nextCommand()
        processor2.nextCommand()

        if (processor1.waiting && processor2.waiting) {
            println("${processor2.sendCounter}")
            return
        }
    }
}

fun main(argc: Array<String>) {
    println("Day18:")
    solve()
}


