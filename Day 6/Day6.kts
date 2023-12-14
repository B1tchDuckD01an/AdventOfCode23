import java.io.File
val lines = File("input.txt").readLines()

//speed = distance / time

// holding the button down for 1 mssecond increases it's speed by 1 millimeter per millisecond

fun parseRaces(lines : List<String>): List<Pair<Int,Int>> {

    val times = lines[0].split(":")[1].split(" ").filter{it.isNotEmpty()}
    val distances = lines[1].split(":")[1].split(" ").filter{it.isNotEmpty()}
    val pairs = mutableListOf<Pair<Int,Int>>()
    times.forEachIndexed {
        index, s ->  pairs.add(Pair(s.toInt(),distances[index].toInt()))
    }
    return pairs.toList()
}


fun parseRacept2(lines : List<String>) = Pair(lines[0].split(":")[1].replace(" ","").toLong(),lines[1].split(":")[1].replace(" ","").toLong())


fun raceBoat(race: Pair<Int,Int>):Int
{
    val times = (0 .. race.first)
    val winningTimes = mutableListOf<Int>()

    fun beatsRecord(raceTime: Int, buttonTime:Int, distanceToBeat: Int): Boolean
    {
        if(buttonTime * (raceTime - buttonTime) > distanceToBeat)
            return true
        else return false
    }

    times.forEach { if(beatsRecord(race.first, it, race.second)) {
        winningTimes.add(it)
    } }
    return winningTimes.size
}


fun raceBoatpt2(race: Pair<Long,Long>):Long
{
    val times = (0 .. race.first)
    var winners : Long = 0

    fun beatsRecord(raceTime: Long, buttonTime:Long, distanceToBeat: Long)
    {
        if(buttonTime * (raceTime - buttonTime) > distanceToBeat)
            winners = winners + 1
    }

    times.forEach { beatsRecord(race.first, it, race.second) }
    return winners
}


println( "part 1 ")
println( "_____")
var winning = mutableListOf<Int>()
parseRaces(lines).forEach { winning.add(raceBoat(it)) }
println(winning.reduce{ acc, i -> acc * i })

println("part 2")
println("_____")

var pt2 = raceBoatpt2(parseRacept2(lines))
println(pt2)