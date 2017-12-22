package day21

import utils.getLinesFromResource

private fun task(patterns: List<Pattern>, iterationCount: Int): Int {
    var count = 0
    var field = ".#...####"
    repeat(iterationCount) {
        val items = collectItems(field)
        val replacement = items.map { searchForReplacement(it, patterns) }
        field = convertToField(replacement)
        count = field.filter { it == '#' }.count()
    }
    return count
}

fun convertToField(replacement: List<String>): String {
    if (replacement.size == 1)
        return replacement[0]

    val size = replacement.size
    val blocks = Math.sqrt(size.toDouble()).toInt()
    val blockSize = Math.sqrt(replacement[0].length.toDouble()).toInt()
    return buildString {
        for (i in 0 until blocks) {
            val items = replacement.subList(i * blocks, i * blocks + blocks)

            var steps = items[0].length / blockSize

            var start = 0
            while (steps > 0) {
                for (item in items) {
                    val line = item.substring(start, start + blockSize)
                    append(line)
                }
                start += blockSize
                steps--
            }
        }
    }
}

fun searchForReplacement(it: String, patterns: List<Pattern>): String {
    patterns
            .filter { pattern -> pattern.getVariants().contains(it) }
            .forEach { return it.replacement }
    throw  IllegalArgumentException(it)
}


fun collectItems(field: String): List<String> {
    val length = field.length

    val gridSize = if (field.length % 2 == 0) 2 else 3

    val list = mutableListOf<String>()
    val blockCount = if (gridSize == 2)
        Math.sqrt(length.toDouble() / 4.toDouble()).toInt()
    else
        Math.sqrt(length.toDouble() / 9.toDouble()).toInt()

    val size = Math.sqrt(length.toDouble()).toInt()

    for (row in 0 until blockCount) {
        val lineSize = gridSize * size
        val start = row * lineSize
        val offset = start + lineSize
        val substring = field.substring(start, offset)

        val items = processSubstring(substring, blockCount, gridSize)

        list.addAll(items)
    }

    return list
}

fun processSubstring(substring: String, count: Int, size: Int): List<String> {

    val list = mutableListOf<String>()

    for (i in 0 until count) {
        var start = i * size
        var end = start + size

        val str = buildString {
            repeat(size)
            {
                val subString = substring.substring(start, end)
                append(subString)

                start += count * size
                end = start + size
            }
        }
        list.add(str)
    }
    return list
}

private fun solve() {
    val input = getLinesFromResource("InputDay21.txt")
    val patterns = input.map { it.convertToPattern() }.toList()
    val result1 = task(patterns, 5)
    println("Task1\n$result1")
    val result2 = task(patterns, 18)
    println("Task1\n$result2")
}


private fun String.convertToPattern(): Pattern {
    val index = indexOf("=>")
    val from = substring(0, index)
    val to = substring(index + 3)

    val pattern = from.replace("/", "").trim()
    val replacement = to.replace("/", "").trim()

    return Pattern(pattern, replacement)
}

data class Pattern(private val pattern: String, val replacement: String) {

    fun getVariants(): Set<String> {
        val set = mutableSetOf<String>()
        if (pattern.length == 4) {
            set.add(pattern)

            val p1 = buildString {
                append(pattern[1])
                append(pattern[0])
                append(pattern[3])
                append(pattern[2])
            }
            set.add(p1)

            val p2 = buildString {
                append(pattern[2])
                append(pattern[0])
                append(pattern[3])
                append(pattern[1])
            }
            set.add(p2)

            val p3 = buildString {
                append(pattern[3])
                append(pattern[1])
                append(pattern[2])
                append(pattern[0])
            }
            set.add(p3)

            val p4 = buildString {
                append(pattern[2])
                append(pattern[3])
                append(pattern[0])
                append(pattern[1])
            }
            set.add(p4)

            val p5 = buildString {
                append(pattern[3])
                append(pattern[2])
                append(pattern[1])
                append(pattern[0])
            }
            set.add(p5)

            val p6 = buildString {
                append(pattern[0])
                append(pattern[2])
                append(pattern[1])
                append(pattern[3])
            }
            set.add(p6)

            val p7 = buildString {
                append(pattern[2])
                append(pattern[0])
                append(pattern[3])
                append(pattern[1])
            }
            set.add(p7)
        } else if (pattern.length == 9) {
            set.add(pattern)

            val p2 = buildString {
                append(pattern[2])
                append(pattern[1])
                append(pattern[0])

                append(pattern[5])
                append(pattern[4])
                append(pattern[3])

                append(pattern[8])
                append(pattern[7])
                append(pattern[6])
            }
            set.add(p2)

            val p3 = buildString {

                append(pattern[6])
                append(pattern[3])
                append(pattern[0])

                append(pattern[7])
                append(pattern[4])
                append(pattern[1])

                append(pattern[8])
                append(pattern[5])
                append(pattern[2])
            }
            set.add(p3)


            val p4 = buildString {

                append(pattern[8])
                append(pattern[5])
                append(pattern[2])

                append(pattern[7])
                append(pattern[4])
                append(pattern[1])

                append(pattern[6])
                append(pattern[3])
                append(pattern[0])
            }
            set.add(p4)

            val p5 = buildString {

                append(pattern[6])
                append(pattern[7])
                append(pattern[8])

                append(pattern[3])
                append(pattern[4])
                append(pattern[5])

                append(pattern[0])
                append(pattern[1])
                append(pattern[2])
            }
            set.add(p5)

            val p6 = buildString {

                append(pattern[8])
                append(pattern[7])
                append(pattern[6])

                append(pattern[5])
                append(pattern[4])
                append(pattern[3])

                append(pattern[2])
                append(pattern[1])
                append(pattern[0])
            }
            set.add(p6)

            val p7 = buildString {

                append(pattern[0])
                append(pattern[3])
                append(pattern[6])

                append(pattern[1])
                append(pattern[4])
                append(pattern[7])

                append(pattern[2])
                append(pattern[5])
                append(pattern[8])
            }
            set.add(p7)

            val p8 = buildString {
                append(pattern[2])
                append(pattern[5])
                append(pattern[8])

                append(pattern[1])
                append(pattern[4])
                append(pattern[7])

                append(pattern[0])
                append(pattern[3])
                append(pattern[6])
            }
            set.add(p8)

        }
        return set
    }
}

fun main(argc: Array<String>) {
    println("Day21:")
    solve()
}
