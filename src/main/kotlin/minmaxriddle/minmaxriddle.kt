package minmaxriddle

import java.util.*
import kotlin.time.measureTimedValue

// Complete the riddle function below.
fun riddle(arr: Array<Long>): Array<Long> {
    // complete this function
    val mapNumberToMaxWindows = findMaxMinLengthArray(arr)
    val reverseMap = TreeMap<Int, Long>()
    mapNumberToMaxWindows.forEach { (key, value) ->
        if (reverseMap[value] == null || (reverseMap[value] != null && reverseMap[value]!! < key)) {
            reverseMap[value] = key
        }
    }
    val result = mutableListOf<Long>()
    var prev: Long = reverseMap[arr.size]!!
    for (index in arr.size downTo 1) {
        if (reverseMap[index] != null && reverseMap[index]!! > prev) {
            prev = reverseMap[index]!!
        }
        result.add(prev)
    }
    return result.toTypedArray().reversedArray()
}

fun findMaxMinLengthArray(arr: Array<Long>): Map<Long, Int> {
    val resultLeft = findLeft(arr)
    val resultRight = findLeft(arr.reversedArray()).reversedArray()
    val result = hashMapOf<Long, Int>()
    for (index in arr.indices) {
        val total =
            if (index != 0 && index != arr.size - 1) {
                resultLeft[index] + resultRight[index] - 1
            } else {
                resultLeft[index] + resultRight[index]
            }
        if (result[arr[index]] == null || (result[arr[index]] != null && total > result[arr[index]]!!)) {
            result[arr[index]] = total
        }
    }
    return result
}

fun findLeft(arr: Array<Long>): Array<Int> {
    val leftStack = Stack<Int>().apply { push(0) }
    val resultLeft = Array<Int>(arr.size) { 0 }
    for (index in 1 until arr.size) {
        while (leftStack.isNotEmpty() && arr[leftStack.peek()] >= arr[index]) {
            leftStack.pop()
        }
        resultLeft[index] = if (leftStack.isEmpty()) (index + 1) else (index - leftStack.peek())
        leftStack.push(index)
    }
    return resultLeft
}
