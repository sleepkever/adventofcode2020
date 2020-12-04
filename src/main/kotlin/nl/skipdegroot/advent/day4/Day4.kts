import nl.skipdegroot.advent.shared.DataLoader
import java.nio.charset.StandardCharsets

val file = DataLoader().getFile("day4.txt")

val firstResult = file.readText(StandardCharsets.UTF_8).trim(' ', '\n')
        .split("\n\n")
        .map { passportEntry ->
            passportEntry.split(" ", "\n").map {
                val values = it.split(":")
                values.first() to values.last()
            }.toMap()
        }.filter { fields ->
            fields.count { it.key != "cid" } == 7
        }

println("FirstResult ${firstResult.count()}")

val hairColorMatcher = "^#[0-9a-f]{6}$".toRegex()
val passportNumberMatcher = "^[0-9]{9}$".toRegex()

val secondResult = firstResult.asSequence().filter {
    it["byr"]?.toIntOrNull() in 1920..2002
}.filter {
    it["iyr"]?.toIntOrNull() in 2010..2020
}.filter {
    it["eyr"]?.toIntOrNull() in 2020..2030
}.filter {
    val height = it["hgt"]
    height != null &&
            ((height.endsWith("in") && height.removeSuffix("in").toIntOrNull() in 59..76) ||
                    (height.endsWith("cm") && height.removeSuffix("cm").toIntOrNull() in 150..193))
}.filter {
    it["hcl"]?.matches(hairColorMatcher) ?: false
}.filter {
    when (it["ecl"]) {
        "amb", "blu", "brn", "gry", "grn", "hzl", "oth" -> true
        else -> false
    }
}.filter {
    it["pid"]?.matches(passportNumberMatcher) ?: false
}
println("SecondResult ${secondResult.count()}")
