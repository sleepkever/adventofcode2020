import nl.skipdegroot.advent.shared.DataLoader

val example = """shiny gold bags contain 2 dark red bags.
dark red bags contain 2 dark orange bags.
dark orange bags contain 2 dark yellow bags.
dark yellow bags contain 2 dark green bags.
dark green bags contain 2 dark blue bags.
dark blue bags contain 2 dark violet bags.
dark violet bags contain no other bags."""


val bagMustHold = mutableMapOf<String, Map<String, Int>>()

//val input = example.split("\n")
val input = DataLoader().loadData("day7.txt")

val contains = "^(.*) bags contain".toRegex()
val bags = "([0-9]+) (.*?) bags?[.,]".toRegex()


for (line in input) {
    val find = contains.find(line)
    if (find != null) {
        val bag = find.groupValues[1]
        val contains = bags.findAll(line).map { it.groupValues }.map { it[2] to it[1].toInt() }.toMap()
        bagMustHold[bag] = contains
    }
}
println(bagMustHold)

fun findBagAmount(startBag: String): Int =
        bagMustHold[startBag]?.map {
            println("Find ${it.key} times ${it.value}")
            findBagAmount(it.key) * it.value
        }?.sum()?.let { if (it > 1) it + 1 else 1 }.also { println("$startBag contains $it other bags") } ?: 1

println(findBagAmount("shiny gold") - 1)
