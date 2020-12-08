import nl.skipdegroot.advent.shared.DataLoader

val example = """light red bags contain 1 bright white bag, 2 muted yellow bags.
dark orange bags contain 3 bright white bags, 4 muted yellow bags.
bright white bags contain 1 shiny gold bag.
muted yellow bags contain 2 shiny gold bags, 9 faded blue bags.
shiny gold bags contain 1 dark olive bag, 2 vibrant plum bags.
dark olive bags contain 3 faded blue bags, 4 dotted black bags.
vibrant plum bags contain 5 faded blue bags, 6 dotted black bags.
faded blue bags contain no other bags.
dotted black bags contain no other bags."""


val bagCanHoldColor = mutableMapOf<String, MutableSet<String>>()

//val input = example.split("\n")
val input = DataLoader().loadData("day7.txt")

val contains = "^(.*) bags contain".toRegex()
val bags = "([0-9]+) (.*?) bags?[.,]".toRegex()


for (line in input) {
    val find = contains.find(line)
    if (find != null) {
        val bag = find.groupValues[1]
        val canContain = bags.findAll(line).map { it.groupValues }.map { it[2] }.toList()
        for (canContainColor in canContain) {
            bagCanHoldColor.putIfAbsent(canContainColor, mutableSetOf())
            bagCanHoldColor[canContainColor]?.add(bag)
        }
    }
}

val alreadyChecked = mutableSetOf<String>()
val canPutIn = mutableSetOf<String>(*bagCanHoldColor["shiny gold"]?.toTypedArray() ?: throw RuntimeException("cannot find initial bags"))

while (!alreadyChecked.containsAll(canPutIn)) {
    println("can put in $canPutIn")
    println("already checked $alreadyChecked")
    val toFind = canPutIn.subtract(alreadyChecked)
    println("to find $toFind")
    for (find in toFind) {
        val bags = bagCanHoldColor[find]
        if (bags != null) {
            canPutIn.addAll(bags)
        }
        alreadyChecked.add(find)
    }
}
println(canPutIn)
println("Answer 1: ${canPutIn.size}")

