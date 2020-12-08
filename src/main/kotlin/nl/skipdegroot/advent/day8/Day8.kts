import Day8.Program
import nl.skipdegroot.advent.shared.DataLoader

//val input = """nop +0
//acc +1
//jmp +4
//acc +3
//jmp -3
//acc -99
//acc +1
//jmp -4
//acc +6""".split("\n")

val input = DataLoader().loadData("day8.txt")


val instructions = input.map {
    val values = it.split(" ")
    Pair(values[0], values[1].toInt())
}
typealias Program = List<Pair<String, Int>>

data class ProgramResult(
        val successful: Boolean,
        val executionOrder: List<Int>,
        val accumulatorValue: Int
)

fun executeProgram(instructions: Program): ProgramResult {
    val visitedLines = mutableListOf<Int>()
    var position = 0
    var accumulator = 0
    var successful = false
    while (true) {
        if (visitedLines.contains(position)) {
            println("Breaking because i have been to $position before")
            break
        }
        visitedLines.add(position)
        if (instructions.size <= position) {
            println("executed succesfully")
            successful = true
            break
        }
        val instruction = instructions[position];
        println("Position: $position accumulator: $accumulator executing $instruction")


        when (instruction.first) {
            "acc" -> {
                accumulator += instruction.second
                position += 1
            }
            "jmp" -> {
                position += instruction.second
            }
            "nop" -> {
                position += 1
            }
            else ->
                throw RuntimeException("Unknown instruction $instruction")
        }
    }
    return ProgramResult(successful, visitedLines, accumulator)
}

fun swapInstruction(input: Program, instructionId: Int): Program? {
    return input.let {
        when (it[instructionId].first) {
            "nop" -> {
                val list = it.toMutableList()
                list[instructionId] = Pair("jmp", it[instructionId].second)
                return list
            }
            "jmp" -> {
                val list = it.toMutableList()
                list[instructionId] = Pair("nop", it[instructionId].second)
                return list
            }
            else -> null
        }
    }
}

val part1 = executeProgram(instructions)

println("accumulator ${part1.accumulatorValue}")

// part 2, dun dun duuuuun

val triedChangedEntries = mutableListOf<Int>()
var succeeded = false;
while (!succeeded) {
    val tryChangeInstructionNumber = part1.executionOrder.subtract(triedChangedEntries).last()
    triedChangedEntries.add(tryChangeInstructionNumber)
    // Change one executed entry
    val updatedProgram = swapInstruction(instructions, tryChangeInstructionNumber)
    if (updatedProgram != null) {
        val result = executeProgram(updatedProgram)
        println("Swapped instruction $tryChangeInstructionNumber, result $result")
        succeeded = result.successful
    } else {
        println("Instruction is not jmp or nop")
    }
}

