import java.io.File

val lines = File("input.txt").readLines()

fun hasHigherValue(pairs: List<Pair<String, Int>>, globalValues: Map<String, Int>): Boolean {
    return pairs.any { (key, value) ->
        globalValues[key]?.let { globalValue ->
            value > globalValue
        } ?: false
    }
}

val blocks = mapOf(
    "red" to 12,
    "green" to 13,
    "blue" to 14
)

fun parseGamesPart1(strings: List<String>) : MutableMap<Int,Boolean> {
    var possibilities = mutableMapOf<Int,Boolean>()

    strings.forEach {
        //games and make all games false
        val id = it.split(":")[0].split(" ")[1].toInt()
        possibilities[id] = true

        //rounds
        val rounds = it.split(":")[1].split(";")
        for(round in rounds) {
            //blocks
            val pairs = round.split(",").map {
                Pair(it.split(" ")[2], it.split(" ")[1].toInt())
            }
            if(hasHigherValue(pairs, blocks))
                possibilities[id] = false
        }
    }
    return possibilities
}

fun parseGamesPart2(strings: List<String>) : Int{
    var possibilities = 0


    strings.forEach {
        //rounds
        val rounds = it.split(":")[1].split(";")
        var blockmap = mutableMapOf("red" to 0, "blue" to 0, "green" to 0)
        for(round in rounds) {
            round.split(",").map {
                Pair(it.split(" ")[2], it.split(" ")[1].toInt())}.forEach{
                val a = blockmap[it.first] ?: 0
                    if(a < it.second)
                        blockmap[it.first] = it.second
            }
        }
        possibilities += blockmap.values.reduce { acc, i -> acc * i}
    }
    return possibilities
}

println("part 1 ")
println(parseGamesPart1(lines).filter{ it.value == true}.keys.sum())

println("part 2 ")
println(parseGamesPart2(lines))
