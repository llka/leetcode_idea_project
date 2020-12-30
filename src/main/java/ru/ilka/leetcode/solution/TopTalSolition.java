package ru.ilka.leetcode.solution;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;

public class TopTalSolition {
    //task 2
    public int solution(String S, int[] X, int[] Y) {
        int dotsCounter = 0;

        TreeMap<Long, List<Dot>> dotsGroupsSortedByDistance = new TreeMap<>();
        for (int i = 0; i < X.length; i++) {
            Dot dot = new Dot(S.charAt(i), X[i], Y[i]);
            if (dotsGroupsSortedByDistance.containsKey(dot.getDistanceToCenter())) {
                dotsGroupsSortedByDistance.get(dot.getDistanceToCenter()).add(dot);
            } else {
                List<Dot> list = new ArrayList<>();
                list.add(dot);
                dotsGroupsSortedByDistance.put(dot.getDistanceToCenter(), List.of(dot));
            }
        }


        Set<Character> dotsTagsSet = new HashSet<>(27);

        for (List<Dot> dotsList : dotsGroupsSortedByDistance.values()) {
            int batchCounter = 0;
            for (Dot dot : dotsList) {
                if (dotsTagsSet.contains(dot.getTag())) {
                    return dotsCounter;
                } else {
                    dotsTagsSet.add(dot.getTag());
                    batchCounter++;
                }
            }
            dotsCounter += batchCounter;
        }

        return dotsCounter;
    }

    //https://stackoverflow.com/questions/26199864/whats-the-optimal-way-to-merge-k-lists

    /**
     * Suppose you're given a merge function that will merge (find the union of) two lists L1 and L2
     * of size s1 and s2 in s1+s2 time. What is the optimal way to merge k lists of size s1, s2, ..., sk?
     *
     * @param A array of sizes of lists
     * @return Minimum time for merging lists from A array.
     */
    public int mergeListsInBestOrder(int[] A) {
        if (A.length < 2) {
            return 0;
        }

        TreeMap<Integer, Integer> sortedMap = new TreeMap<>();
        Arrays.stream(A).forEach(length -> putOrIncrementCounterIfAlreadyExists(length, sortedMap));

        int result = 0;

        while (!sortedMap.isEmpty() && (sortedMap.size() > 1 || sortedMap.firstEntry().getValue() > 1)) {
            int first = sortedMap.firstKey();
            removeOrDecrementCounterIfStillExist(first, sortedMap);
            int second = sortedMap.firstKey();
            removeOrDecrementCounterIfStillExist(first, sortedMap);

            int currentSum = first + second;
            putOrIncrementCounterIfAlreadyExists(currentSum, sortedMap);

            result += currentSum;
        }
        return result;
    }

    private void removeOrDecrementCounterIfStillExist(Integer key, Map<Integer, Integer> map) {
        if (map.get(key) > 1) {
            map.put(key, map.get(key) - 1);
        } else {
            map.remove(key);
        }
    }

    private void putOrIncrementCounterIfAlreadyExists(Integer key, Map<Integer, Integer> map) {
        map.put(key, map.getOrDefault(key, 0) + 1);
    }

    static class Dot {
        Character tag;
        Long x;
        Long y;
        Long distanceToCenter;

        public Dot(char tag, int x, int y) {
            this.tag = tag;
            this.x = (long) x;
            this.y = (long) y;
            this.distanceToCenter = distance(this.x, this.y);
        }

        private Long distance(Long x, Long y) {
            return x * x + y * y;
        }

        public char getTag() {
            return tag;
        }

        public Long getX() {
            return x;
        }

        public Long getY() {
            return y;
        }

        public Long getDistanceToCenter() {
            return distanceToCenter;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Dot dot = (Dot) o;
            return tag == dot.tag &&
                    x.equals(dot.x) &&
                    y.equals(dot.y) &&
                    distanceToCenter.equals(dot.distanceToCenter);
        }

        @Override
        public int hashCode() {
            return Objects.hash(tag, x, y, distanceToCenter);
        }

        @Override
        public String toString() {
            return "Dot{" +
                    "tag=" + tag +
                    ", x=" + x +
                    ", y=" + y +
                    ", distanceToCenter=" + distanceToCenter +
                    '}';
        }
    }
}
