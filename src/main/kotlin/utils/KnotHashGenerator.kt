package utils

import day10.computeKnotTying

object KnotHashGenerator {

    fun generateHash(input: String): String {

        val lengths = input.map { it.toInt() }.toMutableList()
        val additionalSequence = listOf(17, 31, 73, 47, 23)
        lengths.addAll(additionalSequence)

        val data = computeKnotTying(lengths, 64)

        val hash = (0..15)
                .map { data.sliceArray(it * 16..it * 16 + 15) }
                .map {
                    it.fold(0)
                    { acc, value -> acc xor value }
                }
                .map { Integer.toHexString(it) }
                .joinToString(separator = "") { format(it) }

        return hash
    }

    private fun format(it: String): String {
        val v = it.format("%02H")
        if (v.length == 1)
            return '0' + v
        return v
    }
}