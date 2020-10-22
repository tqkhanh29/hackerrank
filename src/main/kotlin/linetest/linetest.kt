package linetest

fun fizzbuzz(input: Int) {
    for (i in 1..input) {
        if (i % 3 == 0 && i % 5 == 0) {
            println("FizzBuzz")
        } else if (i % 3 == 0) {
            println("Fizz")
        } else if (i % 5 == 0) {
            println("Buzz")
        } else {
            println(i)
        }
    }
}

fun findNumbers(arrayParam: IntArray, targetParam: Int): IntArray {
    val mapSum = hashMapOf<Int, Int>()
    arrayParam.forEachIndexed { index, value ->
        val previousIndex = mapSum[targetParam - value]
        if (previousIndex == null) {
            mapSum[value] = index
        } else {
            return intArrayOf(previousIndex, index)
        }
    }
    return intArrayOf()
}