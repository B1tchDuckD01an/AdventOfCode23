import java.io.File
import java.math.BigInteger

val lines = File("test.txt").readLines()

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
    return parts.filter { it.second == true }.map { it.first}.sum()
}

fun getGearPositions(map: MutableMap<Position, Char>): Map<Position, Char>
{
    return map.filter{ it.value in "*" }
}

fun searchParts(fullmap : MutableMap<Position, Char>, gears:  Map<Position, Char>)
{
    gears.keys.forEach{


    }

fun searchFullPart(map: Map<Position, Char>, digitPosition: Position):Int {

    var start = Position(-2,-2)
    var end = Position(-2,-2)

    fun getStart(pos: Position) {

        // Search left
        println("Searching $pos for start ")

        val char = map[pos]!!

        if (char.isDigit()) {
            println("Found $char is a digit, looking left")
            if (pos.x != 0) {
                getStart(pos.left())
            } else {
                // Update the start variable only when there are no more digits to the left
                println("$pos is the start")
                start = pos
            }

        }

    }

    fun getEnd(pos: Position) {
        //search left
        println("searching $pos")
        val char = map[pos]!!
        if(pos.right().x != 140)
        {
        if (char.isDigit()) {
           // println("found " + char + " is digit, looking right")
            getEnd(pos.right())
        } else {
            println("$pos is the end")
            end = pos
        }
    }
    }

    var part = mutableListOf<Char>()
    println("x = " + digitPosition.x.toString())


    getStart(digitPosition)
    println ("start is : " + start.x.toString())

    getEnd(digitPosition)


    println(" end is : " + end.x.toString() )
    return 1

}

val map = convertToMap(lines)

//val resultpt1 = getSumAdjacentSymbols(map)

//println("part 1 ")

//println(resultpt1)

val gears = getGearPositions(map)


for(gear in gears.keys)
{
    println(gear)
    println(constructIntegers(map,gear))
}

println("part 2 ")
