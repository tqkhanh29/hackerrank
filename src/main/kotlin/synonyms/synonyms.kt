package synonyms

import java.io.BufferedWriter
import java.io.FileInputStream
import java.io.FileWriter
import java.util.*
import kotlin.collections.HashSet

const val SYN = "synonyms"
const val DIFF = "different"

object Solution1 {
    fun synonyms(dictionary: List<Pair<String, String>>, inputs: List<Pair<String, String>>): List<String> {
        val dicMap = createDictionary(dictionary)
        val ret = mutableListOf<String>()
        for (i in inputs.indices) {
            if (isSynonym(dicMap, inputs[i])) {
                ret.add(SYN)
            } else {
                ret.add(DIFF)
            }
        }
        return ret
    }


    fun createDictionary(input: List<Pair<String, String>>): Map<String, Set<String>> {
        val dict = mutableMapOf<String, MutableSet<String>>()
        input.forEach { (key, value) ->
            val keyDict = dict.getOrPut(key) { mutableSetOf() }.apply { add(value) }
            val valDict = dict.getOrPut(value) { mutableSetOf() }.apply { add(key) }
            if (keyDict != valDict) {
                if (keyDict.count() < valDict.count()) {
                    mergeSet(dict, keyDict, valDict)
                    dict[key] = valDict
                } else {
                    mergeSet(dict, valDict, keyDict)
                    dict[value] = keyDict
                }
            }
        }
        return dict
    }

    fun mergeSet(dict: MutableMap<String, MutableSet<String>>, from: MutableSet<String>, to: MutableSet<String>) {
        to.addAll(from)
        from.forEach {
            dict[it] = to
        }
    }

    fun isSynonym(dict: Map<String, Set<String>>, testData: Pair<String, String>): Boolean {
        if (testData.first == testData.second) return true
        return dict[testData.first]?.contains(testData.second) == true
    }
}

object Solution2 {

    fun synonyms(dictionary: List<Pair<String, String>>, inputs: List<Pair<String, String>>): List<String> {
        val createdDict = buildDictionary(dictionary)
        val results = mutableListOf<String>()
        for (pair in inputs) {
            results.add(if (isSyns(pair, createdDict)) SYN else DIFF)
        }
        return results
    }

    fun isSyns(pair: Pair<String, String>, dictionary: List<HashSet<String>>): Boolean {
        if (pair.first == pair.second) {
            return true
        }
        for (syns in dictionary) {
            if (syns.contains(pair.first) && syns.contains(pair.second)) {
                return true
            }
        }
        return false
    }

    fun Stack<HashSet<String>>.printlnOut() {
        val temp = Stack<HashSet<String>>()
        while (this.isNotEmpty()) {
            val current = this.pop()
            temp.push(current)
            println(current.joinToString(", "))
        }
        while (temp.isNotEmpty()) {
            this.push(temp.pop())
        }
    }

    fun buildDictionary(input: List<Pair<String, String>>): List<HashSet<String>> {
        val dictList =
            input.filter { (first, second) -> first != second }.map { (first, second) -> hashSetOf(first, second) }
        val stackDict = Stack<HashSet<String>>().apply {
            dictList.forEach { push(it) }
        }
        var current = stackDict.peek()
        val finalDict = mutableListOf<HashSet<String>>()
        while (stackDict.isNotEmpty()) {
            val tempList = Stack<HashSet<String>>()
            while (stackDict.isNotEmpty() && current.intersect(stackDict.peek()).isEmpty()) {
                tempList.push(stackDict.pop())
            }
            if (stackDict.isNotEmpty()) {
                current.addAll(stackDict.pop())
                while (tempList.isNotEmpty()) {
                    stackDict.push(tempList.pop())
                }
                if (stackDict.isEmpty()) {
                    finalDict.add(current)
                }
            } else {
                while (tempList.isNotEmpty()) {
                    stackDict.push(tempList.pop())
                }
                finalDict.add(current)
                current = stackDict.peek()
            }
        }
        return finalDict
    }
}


fun main() {
    val fileData = loadDataFromFile("testcase/vancuum_round1/test.in")
    val testcases = readTestCase(fileData)
    val results = testcases
        .flatMap { Solution2.synonyms(it.dictionaries, it.data) }

    BufferedWriter(FileWriter("testcase/vancuum_round1/testOut.txt")).use { it.write(results.joinToString("\n")) }
}

private fun readTestCase(lineList: MutableList<String>): MutableList<TestCaseModel> {
    var result = mutableListOf<TestCaseModel>()

    var numberOfTestCase = lineList[0].toInt()
    var startTestCaseLine = 0
    var endDictionaryLine = 0
    var endTestCaseLine = 0
    for (i in 0 until numberOfTestCase) {
        // Each test case include dictionary and data need to verify
        startTestCaseLine = endTestCaseLine + 1
        var valueStartLine = lineList[startTestCaseLine].toInt()
        endDictionaryLine = valueStartLine + startTestCaseLine + 1
        var valueEndLine = lineList[endDictionaryLine].toInt()
        endTestCaseLine = endDictionaryLine + valueEndLine

        var dictionaries = mutableListOf<Pair<String, String>>()
        var data = mutableListOf<Pair<String, String>>()

        // get dictanaries
        for (indexDic in startTestCaseLine + 1 until endDictionaryLine) {
            var rowDicList = lineList[indexDic].split(" ")
            dictionaries.add(Pair(rowDicList[0].toLowerCase(), rowDicList[1].toLowerCase()))
        }

        // get data need to verify
        for (indexData in endDictionaryLine + 1 until endTestCaseLine + 1) {
            var rowDataList = lineList[indexData].split(" ")
            data.add(Pair(rowDataList[0].toLowerCase(), rowDataList[1].toLowerCase()))
        }

        result.add(TestCaseModel(dictionaries, data))
    }
    return result
}

///

data class TestCaseModel(
    val dictionaries: MutableList<Pair<String, String>>,
    val data: MutableList<Pair<String, String>>
)

//

private fun loadDataFromFile(fileName: String): MutableList<String> {
    val lineList = mutableListOf<String>()
    var inputStream: FileInputStream? = null
    var sc: Scanner? = null
    try {
        inputStream = FileInputStream(fileName)
        sc = Scanner(inputStream, "UTF-8")
        while (sc.hasNextLine()) {
            val line: String = sc.nextLine()
            lineList.add(line.trim())
        }
        if (sc.ioException() != null) {
            throw sc.ioException()
        }
    } finally {
        inputStream?.close()
        sc?.close()
    }
    return lineList
}