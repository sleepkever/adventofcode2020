import nl.skipdegroot.advent.shared.DataLoader

val lines = DataLoader().loadData("day3.txt")
val lineLength = lines[0].length
fun isTree(x: Int, y: Int): Boolean =
        lines[x][y % lineLength] == '#'

fun findSlopeCount(xSlope: Int, ySlope: Int): Long {
    var count = 0L
    for (i in lines.indices step xSlope) {
        if (isTree(i, (i / xSlope) * ySlope)) {
            count++
        }
    }
    return count
}

val answerOne = findSlopeCount(1, 3)
println(answerOne)

val answerTwo = arrayOf(Pair(1, 1), Pair(1, 3), Pair(1, 5), Pair(1, 7), Pair(2, 1)).map {
    findSlopeCount(it.first, it.second)
}.also { println(it) }.reduce { acc, i -> acc * i }
println(answerTwo)
