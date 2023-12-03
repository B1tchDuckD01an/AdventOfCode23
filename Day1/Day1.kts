import java.io.File
val lines = File("input.txt").readLines()

fun getFirstLastDigits(str : List<String>) :List<Int> =
    str.map {
    it.filter { it.isDigit() }
}.map { (it.first().toString() + it.last().toString()).toInt()}

println("pt1 ")
//val digits = getFirstLastDigits(lines)
//println(digits.sum())

println("pt 2")

val digitmap = mapOf<String,Int>(
    "one" to 1,
    "two" to 2,
    "three" to 3,
    "four" to 4,
    "five" to 5,
    "six" to 6,
    "seven" to 7,
    "eight" to 8,
    "nine" to 9)

fun getDigits(string: String, map: Map<String,Int>): Int {
    for (key in map.keys) {
        if (string.contains(key)) {
            val value = map[key]
                if(value != null)
                    return value
        }
    }
    return 0
}

//fun runCheck(bool: Boolean, digitlist: MutableList<Int>, charllist: MutableList<Char>)
var values = mutableListOf<Int>()

for(line in lines) {
    val digits = mutableListOf<Int>()
    val chars = mutableListOf<Char>()
    for (char in line) {
            if (char.isDigit()) {
                digits.add(char.toString().toInt())
                chars.clear()
            } else {
                chars.add(char)
                if(chars.joinToString("").length >= 3){
                var digit = getDigits(chars.joinToString(""),digitmap)
                if(digit != 0)
                {
                    digits.add(digit)
                    chars.clear()
                    //incase of shared endings
                    chars.add(char)
                }
            }
            }
    }
    values.add((digits.first().toString() + digits.last().toString()).toInt())
}

println(values)
println(values.sum())