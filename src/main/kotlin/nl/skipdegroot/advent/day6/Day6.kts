import nl.skipdegroot.advent.shared.DataLoader
import java.nio.charset.StandardCharsets

val input = DataLoader().getFile("day6.txt").readText(StandardCharsets.UTF_8)

val data = input.trim(' ', '\n')
        .split("\n\n")
        .map { it.split("\n") }

println(data)


val firstAnswer = data.map { group -> group.flatMap { person -> person.map { it } }.toSet() }.map { it.size }.sum()

println(firstAnswer)

val secondAnswer = data.map { groupData ->
    Group(
            groupData.size,
            groupData.flatMap { person -> person.map { it } }.groupingBy { it }.eachCount()
    )
}.map { group ->
    group.letterFrequency.filter { it.value == group.personAmount }.size
}.sum()

println(secondAnswer)


data class Group(val personAmount: Int, val letterFrequency: Map<Char, Int>)
