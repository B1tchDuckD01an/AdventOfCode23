import java.io.File
import java.math.BigInteger

val lines = File("input.txt").readLines()

data class Position(val x: Int, val y: Int)
{
    fun left():Position{
        return Position(x-1,y )
    }
    fun right():Position{
        return Position(x+1, y)
    }
    fun up(): Position{
        return Position(x,y-1)
    }
    fun down(): Position{
        return Position(x,y+1)
    }
}


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
fun getAdjacentDigits(map: Map<Position, Char>, position: Position): List<Char> {
    val (row, col) = position
    val adjacentPositions = listOf(
            Position(row - 1, col - 1), Position(row - 1, col), Position(row - 1, col + 1),
            Position(row, col - 1),                             Position(row, col + 1),
            Position(row + 1, col - 1), Position(row + 1, col), Position(row + 1, col + 1)
    )

    return adjacentPositions
            .filter { map.containsKey(it) }
            .map { map[it]!! }
            .filter { it.isDigit()  }
}

fun convertToMap(strings: List<String>): MutableMap<Position, Char> {
    val resultMap = mutableMapOf<Position, Char>()

    strings.forEachIndexed { outerIndex, str ->
        str.forEachIndexed { innerIndex, char ->
            resultMap[Position(innerIndex, outerIndex)] = char
        }
    }
    return resultMap
}

fun getSumAdjacentSymbols(map: MutableMap<Position, Char>):Int{
    val part = mutableListOf<Int>()
    val parts = mutableListOf<Pair<Int,Boolean>>()
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
    return parts.filter { it.second == true }.map { it.first}.sum()
}

fun getGearPositions(map: MutableMap<Position, Char>): Map<Position, Char>
{
    return map.filter{ it.value in "*" }
}

fun searchParts(fullmap : MutableMap<Position, Char>, gears:  Map<Position, Char>): MutableMap<Position, MutableList<Int>>{
    fun searchDirections(it: Position, pairs: MutableMap<Position, MutableList<Int>>): MutableMap<Position, MutableList<Int>> {

        //search up
        if (fullmap[it.up()]!!.isDigit()) {
            pairs[it]!!.add(searchFullPart(fullmap,it.up()))
        }
        //search down
        if (fullmap[it.down()]!!.isDigit()) {
            pairs[it]!!.add(searchFullPart(fullmap,it.down()))
        }
        //search left
        if (fullmap[it.left()]!!.isDigit()) {
            pairs[it]!!.add(searchFullPart(fullmap,it.left()))
        }
        //search right
        if (fullmap[it.right()]!!.isDigit()) {
            pairs[it]!!.add(searchFullPart(fullmap, it.right()))
        }
        //search up left
        if (fullmap[it.up().left()]!!.isDigit()) {
            pairs[it]!!.add(searchFullPart(fullmap,it.up().left()))
        }
        //search up right
        if (fullmap[it.up().right()]!!.isDigit()) {
            pairs[it]!!.add(searchFullPart(fullmap,it.up().right()))
        }
        //search down left
        if (fullmap[it.down().left()]!!.isDigit()) {
            pairs[it]!!.add(searchFullPart(fullmap,it.down().left()))
        }
        //search down left
        if (fullmap[it.down().right()]!!.isDigit()) {
            pairs[it]!!.add(searchFullPart(fullmap,it.down().right()))
        }
        return pairs
    }

    val pairs : MutableMap<Position, MutableList<Int>> = mutableMapOf<Position,MutableList<Int>>()

    gears.keys.forEach {
        pairs[it] = mutableListOf<Int>()
        searchDirections(it, pairs)
    }

    pairs.entries.forEach{
        pairs[it.key] =  it.value.distinct().toMutableList()
    }

    return pairs.filter{ it.value.size == 2}.toMutableMap()
}

fun searchFullPart(map: Map<Position, Char>, digitPosition: Position):Int {
    var start = Position(-100,-100)
    var end = Position(-100,-100)
    fun getStart(pos: Position) {
        // Search left
        if(pos.x == 0)
        {
            start = Position(0,digitPosition.y)
            return
        }
            if (map[pos.left()]!!.isDigit()) {
                getStart(pos.left())
            } else {
                // Update the start variable only when there are no more digits to the left
                start = Position(pos.x,digitPosition.y)
                return
            }
    }

    fun getEnd(pos: Position) {
        //search right
        if(pos.x == 139) {
            end = Position(pos.x,digitPosition.y)
            return
        }
        if(map[pos.right()]!!.isDigit())
        {
            getEnd(pos.right())
        }
        else {
            end = Position(pos.x,digitPosition.y)
            return
        }
    }

    getStart(digitPosition)
    getEnd(digitPosition)

    return map.entries.filter { it.key.x >= start.x && it.key.x <= end.x && it.key.y == start.y}
            .fold(mutableListOf<Char>()) { acc, entry -> acc.add(entry.value); acc}
            .joinToString("").toInt()

}

val map = convertToMap(lines)

//val resultpt1 = getSumAdjacentSymbols(map)

//println("part 1 ")

//println(resultpt1)

val gears = getGearPositions(map)

var parts :  MutableMap<Position, MutableList<Int>> = searchParts(map, gears)
println(parts)
var products = parts.map{ it.value.first() * it.value.last() }
println(products.sum())