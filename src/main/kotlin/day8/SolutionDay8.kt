package day8

import utils.getLinesFromResource

data class Command(val operand1: String, val operand2: Int, val operation: String, val reg: String, val cond: String, val value: Int) {

    companion object {
        private val regEx = "(\\w+) (inc|dec) (-?\\d+) if (\\w+) (>|>=|<|<=|==|!=) (-?\\d+)".toRegex()
        fun toCommand(str: String): Command {
            val result = regEx.matchEntire(str)
            result?.let {
                val (op1, operation, op2, register, condition, number) = result.destructured
                return Command(op1, op2.toInt(), operation, register, condition, number.toInt())
            }
            IllegalAccessException("Unable to parse command $this")
            return Command("", 0, "", "", "", 0)
        }
    }
}

class Processor(private val program: List<Command>) {

    private val registers = mutableMapOf<String, Int>()
    private var maxValue = Integer.MIN_VALUE

    fun executeProgram() {

        for (command in program) {
            if (checkCondition(command)) {
                executeCommand(command)
                maxValue = Math.max(maxValue, registers.values.max() ?: Integer.MIN_VALUE)
            }
        }
    }

    private fun executeCommand(command: Command) {
        val value = command.operand2

        val register = command.operand1
        val registerValue = getRegisterValue(command.operand1)

        when {
            command.operation == "inc" -> registers[register] = registerValue + value
            command.operation == "dec" -> registers[register] = registerValue - value
            else -> throw Exception()
        }
    }

    private fun checkCondition(command: Command): Boolean {
        val registerValue = getRegisterValue(command.reg)
        val number = command.value
        return when (command.cond) {
            ">" -> registerValue > number
            ">=" -> registerValue >= number
            "<" -> registerValue < number
            "<=" -> registerValue <= number
            "==" -> registerValue == number
            "!=" -> registerValue != number
            else -> {
                Exception(command.value.toString())
                false
            }
        }
    }

    private fun getRegisterValue(register: String) = registers.getOrDefault(register, 0)

    fun getMaxRegisterValue() = registers.values.max()

    fun getMaxValue() = maxValue
}

private fun solve() {
    val input = getLinesFromResource("InputDay8.txt")
    val commands = input.map { Command.toCommand(it) }

    val processor = Processor(commands)
    processor.executeProgram()

    println("Task 1\n${processor.getMaxRegisterValue()}")
    println("Task 2\n${processor.getMaxValue()}")
}

fun main(argc: Array<String>) {
    println("Day8:")
    solve()
}


