package nl.skipdegroot.advent.day2

import nl.skipdegroot.advent.shared.DataLoader

fun main() {
    val loader = DataLoader()
    val data = loader.loadData("./day2.txt");

    val regex = "^([0-9]+)-([0-9]+) ([a-z]): ([a-z]+)$".toRegex()

    val size = data.map {
        val result = regex.find(it)
        if (result != null) {
            Password(result.groupValues[1].toInt()..result.groupValues[2].toInt(), result.groupValues[3].toCharArray().first(), result.groupValues[4])
        } else {
            throw IllegalStateException("Klopt niet ${it}")
        }
    }.filter {
        val numCharactersInPassword = it.password.filter { char -> char == it.character }.length
        it.range.contains(numCharactersInPassword)
    }.size
}


data class Password(val range: IntRange, val character: Char, val password: String)
