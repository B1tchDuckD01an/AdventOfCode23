import java.io.File
import java.math.BigInteger

val lines = File("input.txt").readLines()

data class Position(val x: Int, val y: Int)

fun getAdjacentSymbols(map: Map<Position, Char>, position: Position): List<Char> {
    val (row, col) = position
    val adjacentPositions = listOf(
        Position(row - 1, col - 1), Position(row - 1, col), Position(row - 1, col + 1),
        Position(row, col - 1),                             Position(row, col + 1),
        Position(row + 1, col - 1), Position(row + 1, col), Position(row + 1, col + 1)
    )

    return adjacentPositions
        .filter { map.containsKey(it) }
        .map { map[it]!! }
        .filter { !it.isDigit() && !(it in ".") }
}


fun convertToMap(strings: List<String>): MutableMap<Position, Char> {
    val resultMap = mutableMapOf<Position, Char>()

    strings.forEachIndexed { outerIndex, str ->
        str.forEachIndexed { innerIndex, char ->
            resultMap[Position(outerIndex, innerIndex)] = char
        }
    }

    return resultMap
}

fun getSumAdjacentSymbols(map: MutableMap<Position, Char>):MutableList<Pair<Int,Boolean>>
{
    var part = mutableListOf<Int>()
    var parts = mutableListOf<Pair<Int,Boolean>>()
    var symbol = false
    for(entry in map.entries)
    {
        //if its not a dot or a number, do the thing
        if(entry.value.isDigit())
        {
            part.add(entry.value.toString().toInt())
            //println(part)
            if(getAdjacentSymbols(map,entry.key).size > 0)
                symbol = true

        }
        if(!entry.value.isDigit() && part.size > 0)
        {
            parts.add(Pair(part.joinToString("").toInt(),symbol))
            symbol = false
            part.clear()
        }
    }
    return parts
}

val resultpt1 = getSumAdjacentSymbols(convertToMap(lines))

println("part 1 ")

println(resultpt1)

println(resultpt1.filter { it.second == true }.map { it.first}.sum())

println("part 2 ")
