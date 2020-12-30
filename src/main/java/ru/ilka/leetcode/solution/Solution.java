package ru.ilka.leetcode.solution;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;

@Service
public class Solution {
    final static int LAND = 1;
    final static int WATER = 0;
    private static final Logger logger = LogManager.getLogger(Solution.class);

    public int rangeBitwiseAnd(int m, int n) {
        int[] current = decimalIntoBinary(m);
        for (int i = m + 1; i <= n; i++) {
            int[] next = decimalIntoBinary(i);

            for (int j = 0; j < next.length; j++) {
                current[j] = (next[j] == 1 && current[j] == 1) ? 1 : 0;
            }
        }

        return binaryIntoDecimal(current);
    }

    private int[] decimalIntoBinary(int num) {
        int[] binary = new int[100];
        int index = 0;
        while (num > 0) {
            binary[index++] = num % 2;
            num = num / 2;
        }
        return binary;
    }

    private int binaryIntoDecimal(int[] binary) {
        int num = 0;
        for (int i = 0; i < binary.length; i++) {
            if (binary[i] != 0) {
                num += Math.pow(2, i);
            }
        }
        return num;
    }

    public int subarraySum(int[] nums, int k) {
        HashMap<Integer, Integer> foundedSumsMap = new HashMap<>();
        foundedSumsMap.put(0, 1);

        int counter = 0;
        int postfixSum = 0;
        for (int i = 0; i < nums.length; i++) {
            postfixSum += nums[i];
            //k = sum(i,j) =  postixSum[j + 1] - postfixSum[i] => postfixSum[i] = postfixSum[j + 1] - k.
            if (foundedSumsMap.containsKey(postfixSum - k)) {
                counter += foundedSumsMap.get(postfixSum - k);
            }
            foundedSumsMap.put(postfixSum, foundedSumsMap.getOrDefault(postfixSum, 0) + 1);
        }

        return counter;
    }

    public long fibonacciDpTabulation(int n) {
        if (n < 2) {
            return n;
        }

        long[] mem = new long[n + 1];
        mem[0] = 0;
        mem[1] = 1;
        for (int i = 2; i <= n; i++) {
            mem[i] = mem[i - 2] + mem[i - 1];
        }
        return mem[n];
    }

    public long fibonacciRec(int n) {
        if (n < 2) {
            return n;
        }
        return fibonacciRec(n - 2) + fibonacciRec(n - 1);
    }

    private long dp(long[] mem, int n) {
        long result = n;
        if (mem[n] == 0) {
            if (n < 2) {
                result = n;
            } else {
                result = dp(mem, n - 2) + dp(mem, n - 1);
            }
            mem[n] = result;
        }
        return result;
    }

    public int maxAreaOfIsland(int[][] grid) {
        if (grid.length == 0) {
            return 0;
        }
        boolean[][] isVisited = new boolean[grid.length][grid[0].length];

        int maxArea = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j] == LAND && !isVisited[i][j]) {
                    int currentIslandArea = traverseIslandAndCountLand(grid, isVisited, i, j);
                    maxArea = Math.max(maxArea, currentIslandArea);
                }
            }
        }
        return maxArea;
    }

    private int traverseIslandAndCountLand(int[][] grid, boolean[][] isVisited, int i, int j) {
        if (isVisited[i][j] || grid[i][j] == WATER) {
            return 0;
        }
        isVisited[i][j] = true;

        int counter = 0;
        if (isNewIslandLand(grid, isVisited, i + 1, j)) {
            counter += traverseIslandAndCountLand(grid, isVisited, i + 1, j);
        }
        if (isNewIslandLand(grid, isVisited, i - 1, j)) {
            counter += traverseIslandAndCountLand(grid, isVisited, i - 1, j);
        }
        if (isNewIslandLand(grid, isVisited, i, j + 1)) {
            counter += traverseIslandAndCountLand(grid, isVisited, i, j + 1);
        }
        if (isNewIslandLand(grid, isVisited, i, j - 1)) {
            counter += traverseIslandAndCountLand(grid, isVisited, i, j - 1);
        }
        return counter;
    }

    private boolean isNewIslandLand(int[][] grid, boolean[][] isVisited, int i, int j) {
        return i >= 0 && i < grid.length &&
                j >= 0 && j < grid[i].length &&
                !isVisited[i][j] &&
                grid[i][j] == LAND;
    }

    public int leftMostColumnWithOne(BinaryMatrix binaryMatrix) {
        final int N = binaryMatrix.dimensions().get(0);
        final int M = binaryMatrix.dimensions().get(1);

        boolean foundOne = false;

        int i = 0;
        int j = M - 1;

        while (i < N && j >= 0) {
            if (binaryMatrix.get(i, j) == 0) {
                i++;
            } else {
                // binaryMatrix.get(i, j) == 1
                foundOne = true;
                j--;
            }
        }

        return !foundOne ? -1 : j;
    }

    private int binarySearchMostLeftOne(BinaryMatrix binaryMatrix, int row, int leftIdx, int rightIdx) {
        if (binaryMatrix.get(row, rightIdx) == 0) {
            return -1;
        }

        int initialRight = rightIdx;
        int mostLeftOne = initialRight + 1;
        while (leftIdx <= rightIdx) {
            int middleIdx = leftIdx + (rightIdx - leftIdx) / 2;

            if (binaryMatrix.get(row, middleIdx) == 1) {
                if (middleIdx < mostLeftOne) {
                    mostLeftOne = middleIdx;
                }
                if (middleIdx > 0 && binaryMatrix.get(row, middleIdx - 1) == 1) {
                    rightIdx = middleIdx - 1;
                } else {
                    return mostLeftOne;
                }
            } else {
                leftIdx = middleIdx + 1;
            }
        }
        return mostLeftOne == initialRight + 1 ? -1 : mostLeftOne;
    }

    // This is the BinaryMatrix's API interface.
    // You should not implement it, or speculate about its implementation
    interface BinaryMatrix {
        public int get(int x, int y);

        public List<Integer> dimensions();
    }


}
