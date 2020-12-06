import nl.skipdegroot.advent.shared.DataLoader

val input = DataLoader().loadData("day5.txt")

val tickets = input.map {
    Pair(
            it.take(7).replace("F", "0").replace("B", "1").toInt(2),
            it.takeLast(3).replace("L", "0").replace("R", "1").toInt(2)
    )
}.also { println(it) }

val firstAnswer = tickets.map {
    it.first * 8 + it.second
}.maxOrNull()
println("firstAnswer $firstAnswer")


val column = tickets.groupBy { it.second }.filter { chairMap ->
    val firstChair = chairMap.value.minOf { it.first }
    val lastChair = chairMap.value.maxOf { it.first }
    lastChair - firstChair == chairMap.value.size
}.keys.first()
val row = tickets.filter { it.second == column }.map { it.first }.sorted().filterIndexed { index, i -> index + 6 != i }.first() - 1
println("missing seat $row $column seatid ${row * 8 + column}")
