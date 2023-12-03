import java.io.File
val lines = File("test.txt").readLines()

/*
class Game(id: Int){


}
*/

fun parseGames(strings: List<String>){
    //var games = mutableListOf<Game>()
    strings.forEach {
        val id = it.split(":")[0].split(" ")[1]
        println(id)
        val rounds = it.split(":")[1].split(";")[0]
        rounds.split(",").forEach{
            it.split(" ").forEach {
                println(it)
            }
        }
        println(rounds)
    }
}
parseGames(lines)