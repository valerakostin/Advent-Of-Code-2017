@file:Suppress("JAVA_CLASS_ON_COMPANION")

package utils

import java.nio.file.Files
import java.nio.file.Paths

fun getLinesFromResource(name: String): List<String> {
    val url = String.javaClass.classLoader.getResource(name)
    if (url != null) {
        return Files.readAllLines(Paths.get(url.toURI()))
    }
    return emptyList()
}

fun getLineFromResource(name: String): String {
    val lines = getLinesFromResource(name)
    if (lines.isNotEmpty())
        return lines[0]
    return ""

}
