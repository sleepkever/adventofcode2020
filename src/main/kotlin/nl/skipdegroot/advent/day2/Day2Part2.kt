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
        println(it)
        val firstPosition = it.password.length >= it.range.first && it.password[it.range.first - 1] == it.character
        val secondPosition = it.password.length >= it.range.last && it.password[it.range.last - 1] == it.character
        println(it.password[it.range.last - 1])
        println("$firstPosition $secondPosition")
        firstPosition.xor(secondPosition)
    }.size

    println(size)

}
