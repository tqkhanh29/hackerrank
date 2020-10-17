import java.util.*;
import java.util.stream.Collectors;

public class Solution {

    public static List<Integer> order(
            int cityNodes,
            List<Integer> cityFrom,
            List<Integer> cityTo,
            int company
    ) {
        List<Integer> result = new ArrayList<>();
        ArrayList<Integer> tempFrom = new ArrayList<>(cityFrom);
        ArrayList<Integer> tempTo = new ArrayList<>(cityTo);
        if (!cityFrom.contains(company) && !cityTo.contains(company)) {
            return result;
        }
        Queue<Integer> nextQueue = new LinkedList<>();
        Stack<Integer> traveledIndexes = new Stack<>();
        nextQueue.offer(company);
        while (!nextQueue.isEmpty()) {
            Integer next = nextQueue.poll();
            if (result.isEmpty() || !result.contains(next)) {
                if (next != company) {
                    result.add(0, next);
                }
            }
            for (int i = 0; i < tempFrom.size(); i++) {
                Integer from = tempFrom.get(i);
                Integer to = tempTo.get(i);
                if (from.equals(next)) {
                    traveledIndexes.push(i);
                    nextQueue.offer(to);
                    continue;
                }
                if (to.equals(next)) {
                    traveledIndexes.push(i);
                    nextQueue.offer(from);
                }
            }
            while (!traveledIndexes.isEmpty()) {
                int traveledIndex = traveledIndexes.pop();
                tempFrom.remove(traveledIndex);
                tempTo.remove(traveledIndex);
            }
        }
        Collections.reverse(result);
        return result;
    }


    public static void main(String[] args) {
        List<Integer> result = order(5, Arrays.asList(1,1,2,3,1,4), Arrays.asList(2,3,4,5,5,3), 3);
        result.forEach(System.out::println);
    }
}