import linetest.findNumbers
import linetest.fizzbuzz
import minMoves.minMoves
import minmaxriddle.riddle
import java.io.File

fun zeroSum(arr: Array<Int>): String {
    var sum = 0
    val checkMap = hashMapOf<Int, Int>()
    arr.forEachIndexed { index, value ->
        sum += value
        if (checkMap[sum] != null) {
            return "yes"
        } else {
            checkMap[sum] = index
        }
    }
    return "no"
}

fun riddle2(arr: Array<Long>): Array<Long> {
    // complete this function
    if (arr.size == 1 || arr.size == 2) {
        return arr
    }
    val maxOfMinsMap = hashMapOf<Int, Long>()
    maxOfMinsMap[1] = arr.max()!!
    arr.forEachIndexed { index, value ->
        if (index + 1 >= arr.size) {
            return@forEachIndexed
        }
        var curMin = value
        for (j in (index + 1) until arr.size) {
            curMin = minOf(curMin, arr[j])
            var curSize = j - index + 1
            var curMaxJ = maxOfMinsMap[curSize]
            maxOfMinsMap[curSize] = if (curMaxJ == null) curMin else maxOf(curMin, curMaxJ)
        }
    }
    return maxOfMinsMap.values.toTypedArray()
}

fun main() {
    val sums = findNumbers(intArrayOf(2, 3, 9, 8, 11, 15, 4), 9)
    println(sums.joinToString(", "))
}