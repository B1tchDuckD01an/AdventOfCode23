import java.io.File
val lines = File("test.txt").readLines()



fun getFirstLastDigits(str : List<String>) :List<Int> =
    str.map {
    it.filter { it.isDigit() }
}.map { (it.first().toString() + it.last().toString()).toInt()}


println("pt1 ")
//val digits = getFirstLastDigits(lines)
//println(digits.sum())


println("pt 2")

for( line in lines) {
    val digits = mutableListOf<Int>()
    val chars = mutableListOf<Char>()
    println(line)
    for (char in line) {
        if (char.isDigit()) {
            digits.add(char.toInt())
            chars.clear()
        } else {
            chars.add(char)
            when(chars.joinToString("")) {
                "one" -> {
                    digits.add(1)
                    chars.clear()
                }
                "two" -> {
                    digits.add(2)
                    chars.clear()

                }
                "three" -> {
                    digits.add(3)
                    chars.clear()

                }
                "four" -> {
                    digits.add(4)
                    chars.clear()

                }
                "five" -> {
                    digits.add(5)
                    chars.clear()

                }
            "six" ->{
                digits.add(6)
                chars.clear()

            }
            "seven"-> {
                digits.add(7)
            }
            "eight"->{
                digits.add(8)
                chars.clear()

            }
            "nine"-> {
                digits.add(9)
                chars.clear()

            }
            }
        }
    }

    println(chars)
    println(digits)
}

/*


var sz = 5
val atte = lines.map {
    if(it.length <= 5) {
        replaceText(it,unitmap)
    } else {
    it.windowed(sz,1).map { str ->
            str.map {
                println(str)
                val stuff = str.filter { it.isDigit() }
                if (stuff.length == 0) {
                    println("no digits found, attempt replacing : " + str)
                    replaceText(str, unitmap)
                } else {
                    println("digits found, keeping : " + str)
                    str
                }
            }
        }}}.map{it.toString().filter{ it.isDigit()}}
println(atte)
println(getFirstLastDigits(atte).sum())
