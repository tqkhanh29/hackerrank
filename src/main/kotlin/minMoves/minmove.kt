package minMoves

fun move(arr: Array<Int>): Int {
    val newArr = arr.map { if (it % 2 == 0) 0 else 1 }.toTypedArray()
    return minMoves(newArr)
}

fun minMoves(arr: Array<Int>): Int {
    // Write your code here
    val sortedArr = arr.sortedArray()
    return Math.min(findMinMoves(arr, sortedArr), findMinMoves(arr, sortedArr.reversedArray()))
}

fun findMinMoves(arr: Array<Int>, sortedArray: Array<Int>): Int {
    var totalStep = 0
    var index = 0
    var currentAppear = 0
    var canCount = true
    while (canCount) {
        val nextAppear = findNextAppear(sortedArray[index], currentAppear, arr)
        totalStep += if (sortedArray[index] != arr[index]) (nextAppear - index) else 0
        currentAppear = nextAppear
        canCount = sortedArray[index] == sortedArray[index + 1]
        index += 1
    }
    return totalStep
}

fun findNextAppear(number: Int, currentAppear: Int, arr: Array<Int>): Int {
    var nextAppear = if (currentAppear < arr.size - 1) currentAppear + 1 else currentAppear
    while (arr[nextAppear] != number) {
        nextAppear += 1
    }
    return nextAppear
}