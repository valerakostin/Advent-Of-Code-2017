package day22

import utils.getLinesFromResource

data class Position(val x: Int, val y: Int) {
    fun move(dir: Direction): Position {
        return when (dir) {
            Direction.LEFT -> Position(x - 1, y)
            Direction.RIGHT -> Position(x + 1, y)
            Direction.UP -> Position(x, y - 1)
            Direction.DOWN -> Position(x, y + 1)
        }
    }
}

enum class State {
    CLEAN, WEAK, INFECTED, FLAGGED;
}

enum class Direction {
    UP, DOWN, LEFT, RIGHT;

    fun turnLeft(): Direction {
        return when (this) {
            UP -> LEFT
            LEFT -> DOWN
            DOWN -> RIGHT
            RIGHT -> UP
        }
    }

    fun turnRight(): Direction {
        return when (this) {
            UP -> RIGHT
            RIGHT -> DOWN
            DOWN -> LEFT
            LEFT -> UP
        }
    }

    fun turn(): Direction {
        return when (this) {
            UP -> DOWN
            RIGHT -> LEFT
            DOWN -> UP
            LEFT -> RIGHT
        }
    }
}

private fun task1(positions: MutableSet<Position>, middlePosition: Position) {

    println("Task1")

    var currentPosition = middlePosition
    var direction = Direction.UP

    var causedInfection = 0
    for (i in 0 until 10_000) {

        val infected = (positions.contains(currentPosition))
        direction = if (infected) direction.turnRight() else direction.turnLeft()
        if (infected)
            positions.remove(currentPosition)
        else {
            positions.add(currentPosition)
            causedInfection++
        }
        currentPosition = currentPosition.move(direction)

    }

    println("$causedInfection")
}

private fun task2(positions: MutableMap<Position, State>, current: Position) {

    println("Task2")

    var currentPosition = current
    var direction = Direction.UP

    var causedInfection = 0
    for (i in 0 until 10_000_000) {
        val currentState = positions.getOrDefault(currentPosition, State.CLEAN)

        when (currentState) {
            State.CLEAN -> {
                direction = direction.turnLeft()
                val newState = State.WEAK
                positions[currentPosition] = newState
            }
            State.WEAK -> {
                val newState = State.INFECTED
                causedInfection++
                positions[currentPosition] = newState
            }
            State.INFECTED -> {
                val newState = State.FLAGGED
                direction = direction.turnRight()
                positions[currentPosition] = newState
            }
            State.FLAGGED -> {
                direction = direction.turn()
                positions.remove(currentPosition)
            }
        }
        currentPosition = currentPosition.move(direction)

    }
    println("$causedInfection")
}

private fun solve() {

    val input = getLinesFromResource("InputDay22.txt")

    val positions = convertToPositions(input)
    val initial = Position((input.size / 2), (input[0].length / 2))
    task1(positions, initial)

    val positionsWithState = convertToPositionsWithState(input)
    task2(positionsWithState, initial)
}

private fun convertToPositions(lines: List<String>): MutableSet<Position> {
    val set = mutableSetOf<Position>()
    for (i in 0 until lines.size) {
        val line = lines[i]
        for (j in 0 until line.length) {
            val c = line[j]
            if (c == '#')
                set.add(Position(j, i))
        }
    }
    return set
}

private fun convertToPositionsWithState(lines: List<String>): MutableMap<Position, State> {
    val map = mutableMapOf<Position, State>()
    for (i in 0 until lines.size) {
        val line = lines[i]
        for (j in 0 until line.length) {
            val c = line[j]
            if (c == '#')
                map.put(Position(j, i), State.INFECTED)
        }
    }
    return map
}

fun main(argc: Array<String>) {
    println("Day22:")
    solve()
}
