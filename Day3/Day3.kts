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

fun searchParts(fullmap : MutableMap<Position, Char>, gears:  Map<Position, Char>): MutableList<Pair<Position,Position>> {

    fun addPair(initial: Pair<Position, Position>, pos : Position): Pair<Position, Position> {
        if (initial.first == Position(-100, -100)) {
            return Pair(pos, Position(-100, -100))
        }
        return Pair(initial.first, pos)

    }

    fun searchDirections(it: Position, pairs: MutableList<Pair<Position, Position>>, index: Int): MutableList<Pair<Position, Position>> {

        if (fullmap[it.up()]!!.isDigit()) {
            pairs.set(index, addPair(pairs[index], it.up()))
            if(pairs[index].second.x != -100)
                return pairs
        }

        //search down
        if (fullmap[it.down()]!!.isDigit()) {
            pairs.set(index, addPair(pairs[index], it.down()))
            if(pairs[index].second.x != -100)
                return pairs
        }

//search left
        if (fullmap[it.left()]!!.isDigit()) {
            pairs.set(index, addPair(pairs[index], it.left()))
            if(pairs[index].second.x != -100)
                return pairs
        }

//search right
        if (fullmap[it.right()]!!.isDigit()) {
            pairs.set(index,addPair(pairs[index], it.right()))
            if(pairs[index].second.x != -100)
                return pairs
        }
        //search up left
        if (fullmap[it.up().left()]!!.isDigit()) {
            pairs.set(index,addPair(pairs[index], it.up().left()))
            if(pairs[index].second.x != -100)
                return pairs
        }

        //search up right
        if (fullmap[it.up().right()]!!.isDigit()) {
            pairs.set(index,addPair(pairs[index], it.up().right()))
            if(pairs[index].second.x != -100)
                return pairs
        }

        //search down left
        if (fullmap[it.down().left()]!!.isDigit()) {
            pairs.set(index,addPair(pairs[index], it.down().left()))
            if(pairs[index].second.x != -100)
                return pairs
        }

        //search down left
        if (fullmap[it.down().right()]!!.isDigit()) {
            pairs.set(index,addPair(pairs[index], it.down().right()))
            if(pairs[index].second.x != -100)
                return pairs
        }
        return pairs
    }

    val pairs = mutableListOf<Pair<Position, Position>>()


    gears.keys.forEachIndexed { index, it ->
        pairs.add(index,Pair(Position(-100,-100),Position(-100,-100)))
        searchDirections(it, pairs, index)
    }
 //   println(pairs)
    return pairs
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

gears.forEach {
  println( "$it " +   getAdjacentDigits(map, it.key))
}
var products = mutableListOf<Long>()

var product :Long = 0
var parts = searchParts(map, gears)

parts.filter { it.second.x != -100}.forEach {
    val start = searchFullPart(map,it.first)
    val end = searchFullPart(map,it.second)

    products.add((start * end).toLong())
    product += start*end
}

